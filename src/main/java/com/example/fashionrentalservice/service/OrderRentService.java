package com.example.fashionrentalservice.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.fashionrentalservice.exception.CreateOrderFailed;
import com.example.fashionrentalservice.exception.CusNotFoundByID;
import com.example.fashionrentalservice.exception.PONotFoundByID;
import com.example.fashionrentalservice.exception.ProductIsRented;
import com.example.fashionrentalservice.exception.ProductNotAvailable;
import com.example.fashionrentalservice.exception.ProductNotForSale;
import com.example.fashionrentalservice.exception.ProductNotFoundByID;
import com.example.fashionrentalservice.exception.WalletCusNotFound;
import com.example.fashionrentalservice.exception.handlers.CrudException;
import com.example.fashionrentalservice.model.dto.account.CustomerDTO;
import com.example.fashionrentalservice.model.dto.account.ProductOwnerDTO;
import com.example.fashionrentalservice.model.dto.account.TransactionHistoryDTO;
import com.example.fashionrentalservice.model.dto.account.WalletDTO;
import com.example.fashionrentalservice.model.dto.order.OrderRentDTO;
import com.example.fashionrentalservice.model.dto.order.OrderRentDTO.OrderRentStatus;
import com.example.fashionrentalservice.model.dto.order.OrderRentDetailDTO;
import com.example.fashionrentalservice.model.dto.product.ProductDTO;
import com.example.fashionrentalservice.model.dto.product.ProductDTO.checkTypeSaleorRentorSaleRent;
import com.example.fashionrentalservice.model.request.OrderRentDetailRequestEntity;
import com.example.fashionrentalservice.model.request.OrderRentRequestEntity;
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
						throw new ProductIsRented(product.getProductName());
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

        rentRepo.saveAll(listRent);
        rentDetailRepo.saveAll(listOrderRentDetail);
		productService.updateListProductStatusExceptRentingStatus(listProduct);
//
//        
//        
//        for (OrderBuyDTO x : listOrder) {
//        	WalletDTO checkCus = walletService.updateBalanceReturnDTO(x.getCustomerDTO().getAccountDTO().getWalletDTO().getWalletID(), x.getTotal());
//        	if(checkCus == null) 
//        		throw new WalletInOrderServiceFailed();
//        	
//        	listWallet.add(checkCus);
//        	
//        	WalletDTO checkPo = walletService.updatePendingMoneyReturnDTO(x.getProductownerDTO().getAccountDTO().getWalletDTO().getWalletID(), x.getTotal());
//        	if(checkPo == null) 
//        		throw new WalletInOrderServiceFailed();
//        	
//        	listWallet.add(checkPo);
//        	
//        	TransactionHistoryDTO cusBuyTrans = TransactionHistoryDTO.builder()
//        													.amount(x.getTotal())
//        													.transactionType("Mua")
//        													.description("thanh toan hóa đơn: " + x.getOrderBuyID() + ". -" +x.getTotal() + " VND")
//        													.orderBuyDTO(x)
//        													.accountDTO(x.getCustomerDTO().getAccountDTO())
//        													.build();      												
//        	TransactionHistoryDTO checkCusTrans = transService.createBuyTransactionHistoryReturnDTO(cusBuyTrans);
//        	if(checkCusTrans == null) 
//        		throw new TransactionHistoryCreatedFailed();
//        	
//        	listTrans.add(checkCusTrans);
//        	
//        	TransactionHistoryDTO poBuyTrans = TransactionHistoryDTO.builder()
//															.amount(x.getTotal())
//															.transactionType("Mua")
//															.description("Nhận tiền từ hóa đơn: " + x.getOrderBuyID() + ". +" +x.getTotal() + " VND ")
//															.orderBuyDTO(x)
//															.accountDTO(x.getProductownerDTO().getAccountDTO())
//															.build();
//        	TransactionHistoryDTO checkPOTrans = transService.createBuyTransactionHistoryReturnDTO(poBuyTrans);
//        	if(checkPOTrans == null) 
//        		throw new TransactionHistoryCreatedFailed();
//        	listTrans.add(checkPOTrans);
//		}
//        
//        walletRepo.saveAll(listWallet);
//        transRepo.saveAll(listTrans);

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

	// ================================== UpdateOrderStatus // ========================================
//	public OrderBuyResponseEntity updateOrderBuyByOrderBuyID(int orderBuyID, OrderBuyStatus status) throws CrudException {
//		OrderBuyDTO check = rentRepo.findById(orderBuyID).orElse(null);
//		if (check == null)
//			throw new OrderBuyNotFoundFailed();
//		WalletDTO checkWalletPO = check.getProductownerDTO().getAccountDTO().getWalletDTO();
//		WalletDTO checkWalletCus = check.getCustomerDTO().getAccountDTO().getWalletDTO();
//
//		if (checkWalletPO == null) {
//			throw new WalletPoNotFound();
//		}
//		if (checkWalletCus == null) {
//			throw new WalletCusNotFound();
//		}
//
//		if (status == OrderBuyStatus.COMPLETED)
//			walletService.updatePendingMoneyToBalanceReturnDTO(checkWalletPO.getWalletID(), check.getTotal());
//
//		if (status == OrderBuyStatus.CANCELED)
//			walletService.updatePendingMoneyToCustomerBalanceReturnDTO(checkWalletCus, checkWalletPO, check.getTotal());
//
//		check.setStatus(status);
//		return OrderBuyResponseEntity.fromOrderBuyDTO(buyRepo.save(check));
//	}

}
