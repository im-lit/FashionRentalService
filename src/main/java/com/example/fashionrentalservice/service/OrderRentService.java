package com.example.fashionrentalservice.service;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.fashionrentalservice.exception.CreateOrderFailed;
import com.example.fashionrentalservice.exception.CusNotFoundByID;
import com.example.fashionrentalservice.exception.OrderBuyNotFoundFailed;
import com.example.fashionrentalservice.exception.PONotFoundByID;
import com.example.fashionrentalservice.exception.ProductIsRented;
import com.example.fashionrentalservice.exception.ProductNotAvailable;
import com.example.fashionrentalservice.exception.ProductNotForSale;
import com.example.fashionrentalservice.exception.ProductNotFoundByID;
import com.example.fashionrentalservice.exception.TransactionHistoryCreatedFailed;
import com.example.fashionrentalservice.exception.WalletCusNotFound;
import com.example.fashionrentalservice.exception.WalletInOrderServiceFailed;
import com.example.fashionrentalservice.exception.WalletPoNotFound;
import com.example.fashionrentalservice.exception.handlers.CrudException;
import com.example.fashionrentalservice.model.dto.account.CustomerDTO;
import com.example.fashionrentalservice.model.dto.account.ProductOwnerDTO;
import com.example.fashionrentalservice.model.dto.account.TransactionHistoryDTO;
import com.example.fashionrentalservice.model.dto.account.WalletDTO;
import com.example.fashionrentalservice.model.dto.order.OrderBuyDTO.OrderBuyStatus;
import com.example.fashionrentalservice.model.dto.order.OrderRentDTO;
import com.example.fashionrentalservice.model.dto.order.OrderRentDTO.OrderRentStatus;
import com.example.fashionrentalservice.model.dto.order.OrderRentDetailDTO;
import com.example.fashionrentalservice.model.dto.product.ProductDTO;
import com.example.fashionrentalservice.model.dto.product.ProductDTO.checkTypeSaleorRentorSaleRent;
import com.example.fashionrentalservice.model.request.OrderRentDetailRequestEntity;
import com.example.fashionrentalservice.model.request.OrderRentRequestEntity;
import com.example.fashionrentalservice.model.response.OrderBuyResponseEntity;
import com.example.fashionrentalservice.model.response.OrderRentResponseEntity;
import com.example.fashionrentalservice.repositories.CustomerRepository;
import com.example.fashionrentalservice.repositories.OrderRentDetailRepository;
import com.example.fashionrentalservice.repositories.OrderRentRepository;
import com.example.fashionrentalservice.repositories.ProductOwnerRepository;
import com.example.fashionrentalservice.repositories.ProductRepository;
import com.example.fashionrentalservice.repositories.TransactionHistoryRepository;
import com.example.fashionrentalservice.repositories.WalletRepository;

@Service
public class OrderRentService {

	@Autowired
	private CustomerRepository cusRepo;

	@Autowired
	private ProductOwnerRepository poRepo;

	@Autowired
	private OrderRentRepository rentRepo;

	@Autowired
	private OrderRentDetailRepository rentDetailRepo;

	@Autowired
	private ProductRepository productRepo;

	@Autowired
	private ProductService productService;

	@Autowired
	private TransactionHistoryService transService;

	@Autowired
	private TransactionHistoryRepository transRepo;

	@Autowired
	private WalletService walletService;

	@Autowired
	private WalletRepository walletRepo;

	@Autowired
	private OrderRentDetailService renDetailService;

//================================== Tạo mới OrderBuy - ========================================
	public List<OrderRentResponseEntity> createOrderRent(List<OrderRentRequestEntity> entity) throws CrudException {
		List<OrderRentDTO> listRent = new ArrayList<>();
		List<OrderRentDetailDTO> listOrderRentDetail = new ArrayList<>();
		List<ProductDTO> listProduct = new ArrayList<>();
		List<WalletDTO> listWallet = new ArrayList<>();
		List<TransactionHistoryDTO> listTrans = new ArrayList<>();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		DecimalFormat decimalFormat = new DecimalFormat("#,###,###,###");

		for (OrderRentRequestEntity x : entity) {
			CustomerDTO cus = cusRepo.findById(x.getCustomerID()).orElse(null);
			ProductOwnerDTO po = poRepo.findById(x.getProductownerID()).orElse(null);
			if (cus == null)
				throw new CusNotFoundByID();
			if (po == null)
				throw new PONotFoundByID();
			if (cus.getAccountDTO().getWalletDTO() == null)
				throw new WalletCusNotFound();
			if (po.getAccountDTO().getWalletDTO() == null)
				throw new WalletCusNotFound();

			OrderRentDTO orderRent = OrderRentDTO.builder()
									.total(x.getTotal())
									.totalRentPriceProduct(x.getTotalRentPriceProduct())
									.shippingFee(x.getShippingFee())
									.cocMoneyTotal(x.getCocMoneyTotal())
									.dateOrder(LocalDate.now())
									.status(OrderRentStatus.PENDING).customerAddress(x.getCustomerAddress()).customerDTO(cus)
									.productownerDTO(po).build();
			for (OrderRentDetailRequestEntity detail : x.getOrderRentDetail()) {				
				ProductDTO product = productRepo.findById(detail.getProductID()).orElse(null);
				if (product == null)
					throw new ProductNotFoundByID();
				if (product.getStatus() == ProductDTO.ProductStatus.SOLD_OUT) {
					throw new ProductNotAvailable();
				} else if (product.getCheckType() == checkTypeSaleorRentorSaleRent.SALE) {
					throw new ProductNotForSale();
				} else if (product.getStatus() == ProductDTO.ProductStatus.RENTING) {
					LocalDate newDate = LocalDate.parse(detail.getStartDate(), formatter);
					List<OrderRentDetailDTO> list = renDetailService.getOrderRentDetailByProductIDAndCheckDate(product.getProductID(), newDate);
					if (list.size() != 0) {
						int lastIndex = list.size() - 1;
						OrderRentDetailDTO checkDate = list.get(lastIndex);
						throw new ProductIsRented(product.getProductName(), checkDate.getEndDate());
					}
				}

				OrderRentDetailDTO detailRent = OrderRentDetailDTO.builder()
												.productDTO(product)
												.cocMoney(detail.getCocMoney())
												.startDate(LocalDate.parse(detail.getStartDate(), formatter))
												.endDate(LocalDate.parse(detail.getEndDate(), formatter))
												.orderRentDTO(orderRent)
												.rentPrice(detail.getRentPrice()).build();
				listOrderRentDetail.add(detailRent);
				listProduct.add(detailRent.getProductDTO());
			}
			listRent.add(orderRent);
		}
		
        for (OrderRentDTO x : listRent) {
        	WalletDTO checkCus = walletService.updateCusBalanceReturnDTO(x.getCustomerDTO().getAccountDTO().getWalletDTO().getWalletID(), x.getTotal(), x.getCocMoneyTotal());
        	if(checkCus == null) 
        		throw new WalletInOrderServiceFailed();
        	listWallet.add(checkCus);
        	
        	WalletDTO checkPo = walletService.updatePOPendingMoneyAndCocMoneyReturnDTO(x.getProductownerDTO().getAccountDTO().getWalletDTO().getWalletID(), x.getTotalRentPriceProduct(), x.getCocMoneyTotal());
        	if(checkPo == null) 
        		throw new WalletInOrderServiceFailed();
        	listWallet.add(checkPo);
        	
        	String totalFormarted = decimalFormat.format(x.getTotal());
        	String cocMoneyFormarted = decimalFormat.format(x.getCocMoneyTotal());
        	String totalRentPriceFormarted = decimalFormat.format(x.getTotalRentPriceProduct());
        	TransactionHistoryDTO cusBuyTrans = TransactionHistoryDTO.builder()
        													.amount(x.getTotal())
        													.transactionType("Thuê")
        													.description("thanh toán hóa đơn thuê: -" + totalFormarted + " VND. "
        															+ "Tiền cọc sản phẩm: -" + cocMoneyFormarted + " VND")
        													.orderRentDTO(x)
        													.accountDTO(x.getCustomerDTO().getAccountDTO())
        													.build();      												
        	TransactionHistoryDTO checkCusTrans = transService.createBuyTransactionHistoryReturnDTO(cusBuyTrans);
        	if(checkCusTrans == null) 
        		throw new TransactionHistoryCreatedFailed();
        	listTrans.add(checkCusTrans);
        	
        	TransactionHistoryDTO poBuyTrans = TransactionHistoryDTO.builder()
															.amount(x.getTotalRentPriceProduct())
															.transactionType("Thuê")
															.description("Nhận tiền từ hóa đơn thuê:  +" + totalRentPriceFormarted + " VND. "
																	+ "Nhận tiền cọc sản phẩm: +" + cocMoneyFormarted + " VND")
															.orderRentDTO(x)
															.accountDTO(x.getProductownerDTO().getAccountDTO())
															.build();
        	TransactionHistoryDTO checkPOTrans = transService.createBuyTransactionHistoryReturnDTO(poBuyTrans);
        	if(checkPOTrans == null) 
        		throw new TransactionHistoryCreatedFailed();
        	listTrans.add(checkPOTrans);
		}
        rentRepo.saveAll(listRent);
        rentDetailRepo.saveAll(listOrderRentDetail);
		productService.updateListProductStatusExceptRentingStatus(listProduct);  
        walletRepo.saveAll(listWallet);
        transRepo.saveAll(listTrans);

		try {
			return OrderRentResponseEntity.fromListOrderRentDTO(listRent);
		} catch (Exception e) {
			e.printStackTrace();
			throw new CreateOrderFailed();
		}
	}

//================================== Lay tat ca OrderRent by CustomerID========================================
	public List<OrderRentResponseEntity> getAllOrderRentByCustomerID(int customerID) {
		return OrderRentResponseEntity.fromListOrderRentDTO(rentRepo.findAllOrderRentByCustomerID(customerID));
	}

	// ================================== Lay tat ca OrderRent by ProductOwnerID========================================
	public List<OrderRentResponseEntity> getAllOrderRentByProductOwnerID(int productownerID) {
		return OrderRentResponseEntity.fromListOrderRentDTO(rentRepo.findAllOrderRentByProductOwnerID(productownerID));
	}

	// ================================== Lay tat ca Order// ========================================
	public List<OrderRentResponseEntity> getAllOrder() {
		return OrderRentResponseEntity.fromListOrderRentDTO(rentRepo.findAll());
	}

	// ================================== Lay tat ca OrderRent trong 1 thang by ProductOwnerID ========================================
	public List<OrderRentResponseEntity> getTotal1MonthOrderByProductOwnerID(int productOwnerID) {
		LocalDate startDate = LocalDate.now().minusMonths(1);
		List<OrderRentDTO> list = rentRepo.findTotalOrderRent1MonthByProductOwnerID(productOwnerID, startDate);
		return OrderRentResponseEntity.fromListOrderRentDTO(list);
	}

	// ================================== Lay tat ca OrderRent trong 1 thang by ProductOwnerID ========================================
	public List<OrderRentResponseEntity> getTotalOrderRent1WeekOrderByProductOwnerID(int productOwnerID) {
		LocalDate startDate = LocalDate.now().minusWeeks(1);
		List<OrderRentDTO> list = rentRepo.findTotalOrderRent1WeekByProductOwnerID(productOwnerID, startDate);
		return OrderRentResponseEntity.fromListOrderRentDTO(list);
	}

// ================================== UpdateOrderRentStatus  ========================================
	public OrderRentResponseEntity updateOrderRentByOrderRentID(int orderRentID, OrderRentStatus status) throws CrudException {
		OrderRentDTO check = rentRepo.findById(orderRentID).orElse(null);
		if (check == null)
			throw new OrderBuyNotFoundFailed();
		WalletDTO checkWalletPO = check.getProductownerDTO().getAccountDTO().getWalletDTO();
		WalletDTO checkWalletCus = check.getCustomerDTO().getAccountDTO().getWalletDTO();

		if (checkWalletPO == null) 
			throw new WalletPoNotFound();
		if (checkWalletCus == null) 
			throw new WalletCusNotFound();
		

		if (status == OrderRentStatus.COMPLETED)
			walletService.updatePOPendingMoneyToBalanceAndRefundCocMoneyReturnDTO(checkWalletCus, checkWalletPO, check.getTotalRentPriceProduct(), check.getCocMoneyTotal());
		if (status == OrderRentStatus.CANCELED)
			walletService.updatePOPendingMoneyToCusBalanceAndRefundCocMoneyReturnDTO(checkWalletCus, checkWalletPO, check.getTotalRentPriceProduct(), check.getCocMoneyTotal());
		if (status == OrderRentStatus.REJECTING_COMPLETED)
			walletService.updatePOPendingMoneyToCusBalanceAndRefundCocMoneyReturnDTO(checkWalletCus, checkWalletPO, check.getTotalRentPriceProduct(), check.getCocMoneyTotal());
		
		check.setStatus(status);
		
		return OrderRentResponseEntity.fromOrderRentDTO(rentRepo.save(check));

		}
}