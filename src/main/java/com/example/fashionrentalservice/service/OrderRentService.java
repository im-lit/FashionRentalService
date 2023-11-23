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
import com.example.fashionrentalservice.exception.PendingMoneyNegative;
import com.example.fashionrentalservice.exception.ProductIsRented;
import com.example.fashionrentalservice.exception.ProductNotAvailable;
import com.example.fashionrentalservice.exception.ProductNotForSale;
import com.example.fashionrentalservice.exception.ProductNotFoundByID;
import com.example.fashionrentalservice.exception.TransactionHistoryCreatedFailed;
import com.example.fashionrentalservice.exception.WalletCusNotFound;
import com.example.fashionrentalservice.exception.WalletInOrderServiceFailed;
import com.example.fashionrentalservice.exception.WalletPoNotFound;
import com.example.fashionrentalservice.exception.handlers.CrudException;
import com.example.fashionrentalservice.model.dto.account.AccountDTO.AccountStatus;
import com.example.fashionrentalservice.model.dto.account.CustomerDTO;
import com.example.fashionrentalservice.model.dto.account.ProductOwnerDTO;
import com.example.fashionrentalservice.model.dto.account.TransactionHistoryDTO;
import com.example.fashionrentalservice.model.dto.account.WalletDTO;
import com.example.fashionrentalservice.model.dto.order.OrderRentDTO;
import com.example.fashionrentalservice.model.dto.order.OrderRentDTO.OrderRentStatus;
import com.example.fashionrentalservice.model.dto.order.OrderRentDetailDTO;
import com.example.fashionrentalservice.model.dto.product.ProductDTO;
import com.example.fashionrentalservice.model.dto.product.VoucherDTO;
import com.example.fashionrentalservice.model.dto.product.ProductDTO.ProductStatus;
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
import com.example.fashionrentalservice.repositories.VoucherRepository;
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
	
	@Autowired
	private VoucherRepository voRepo;

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
			VoucherDTO voucher = voRepo.findById(x.getVoucherID()).orElse(null);
			if (cus == null)
				throw new CusNotFoundByID();
        	if( cus.getAccountDTO().getStatus() == AccountStatus.NOT_VERIFIED)
        		throw new PendingMoneyNegative("Your account is not verified!");
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
									.voucherDTO(voucher)
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
					OrderRentDetailDTO checkOrder = renDetailService.getOrderRentDetailByProductIDAndCheckDate(product.getProductID(), newDate);
					if (checkOrder != null) 
						throw new ProductIsRented(product.getProductName(), checkOrder.getEndDate());
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
	
	public List<OrderRentResponseEntity> getAllCanceledOrderRent() {
		return OrderRentResponseEntity.fromListOrderRentDTO(rentRepo.findAllCancelOrderRent());
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
		DecimalFormat decimalFormat = new DecimalFormat("#,###,###,###");
		List<TransactionHistoryDTO> listTrans = new ArrayList<>();

		
		if (check == null)
			throw new OrderBuyNotFoundFailed();
		WalletDTO checkWalletPO = check.getProductownerDTO().getAccountDTO().getWalletDTO();
		WalletDTO checkWalletCus = check.getCustomerDTO().getAccountDTO().getWalletDTO();

		if (checkWalletPO == null) 
			throw new WalletPoNotFound();
		if (checkWalletCus == null) 
			throw new WalletCusNotFound();
		
// Thành công thì ghi Log , PO Nhận tiền hóa đơn hoàn tất  và trả tiền cọc, CusTomer nhận lại tiền cọc. 
		if (status == OrderRentStatus.COMPLETED) {	
			walletService.updatePOPendingMoneyToBalanceAndRefundCocMoneyReturnDTO(checkWalletCus, checkWalletPO, check.getTotalRentPriceProduct(), check.getCocMoneyTotal());
			
			List<OrderRentDetailDTO> listOrderRent = new ArrayList<>();
			List<ProductDTO> listProduct = new ArrayList<>();
        	String cocMoneyFormarted = decimalFormat.format(check.getCocMoneyTotal());
        	String totalRentPriceFormarted = decimalFormat.format(check.getTotalRentPriceProduct());
        	TransactionHistoryDTO cusBuyTrans = TransactionHistoryDTO.builder()
        													.amount(check.getCocMoneyTotal())
        													.transactionType("Thuê")
        													.description("Nhận lại tiền cọc từ hóa đơn : +" + cocMoneyFormarted + " VND.")
        													.orderRentDTO(check)
        													.accountDTO(check.getCustomerDTO().getAccountDTO())
        													.build();      												
        	TransactionHistoryDTO checkCusTrans = transService.createBuyTransactionHistoryReturnDTO(cusBuyTrans);
        	if(checkCusTrans == null) 
        		throw new TransactionHistoryCreatedFailed();
        	listTrans.add(checkCusTrans);
        	
        	TransactionHistoryDTO poBuyTrans = TransactionHistoryDTO.builder()
															.amount(check.getTotalRentPriceProduct())
															.transactionType("Thuê")
															.description("Nhận tiền hoàn tất hóa đơn thuê:  +" + totalRentPriceFormarted + " VND vào số dư ví. "
																	+ "Hoàn trả tiền cọc hóa đơn : -" + cocMoneyFormarted + " VND")
															.orderRentDTO(check)
															.accountDTO(check.getProductownerDTO().getAccountDTO())
															.build();
        	TransactionHistoryDTO checkPOTrans = transService.createBuyTransactionHistoryReturnDTO(poBuyTrans);
        	if(checkPOTrans == null) 
        		throw new TransactionHistoryCreatedFailed();
        	
        	listTrans.add(checkPOTrans);
        	
        	listOrderRent = renDetailService.getAllOrderDetailByOrderRentIDReturnDTO(orderRentID);
        	for (OrderRentDetailDTO x : listOrderRent) {
        		ProductDTO prod = x.getProductDTO();
        		prod.setStatus(ProductStatus.AVAILABLE);
        		listProduct.add(prod);
			}
        	
			transRepo.saveAll(listTrans);
			productRepo.saveAll(listProduct);
		}
// Canceled  thì ghi Log , PO trả tiền hóa đơn cho CUS và trả tiền cọc, CusTomer nhận lại tiền cọc + hoàn lại tiền hóa đơn, 
		if (status == OrderRentStatus.CANCELED || status == OrderRentStatus.REJECTING_COMPLETED) {
			walletService.updatePOPendingMoneyToCusBalanceAndRefundCocMoneyReturnDTO(checkWalletCus, checkWalletPO, check.getTotalRentPriceProduct(), check.getCocMoneyTotal());
			
			List<OrderRentDetailDTO> listOrderRent = new ArrayList<>();
			List<ProductDTO> listProduct = new ArrayList<>();
			String cocMoneyFormarted = decimalFormat.format(check.getCocMoneyTotal());
			String totalRentPriceFormarted = decimalFormat.format(check.getTotalRentPriceProduct());
			TransactionHistoryDTO cusBuyTrans = TransactionHistoryDTO.builder()
    													.amount(check.getCocMoneyTotal() + check.getTotalRentPriceProduct())
    													.transactionType("Thuê")
    													.description("Hoàn tiền cọc từ hóa đơn : +" + cocMoneyFormarted + " VND. Hoàn trả tiền hóa đơn: +" + totalRentPriceFormarted + " VND")
    													.orderRentDTO(check)
    													.accountDTO(check.getCustomerDTO().getAccountDTO())
    													.build();      												
			TransactionHistoryDTO checkCusTrans = transService.createBuyTransactionHistoryReturnDTO(cusBuyTrans);
			if(checkCusTrans == null) 
				throw new TransactionHistoryCreatedFailed();
			listTrans.add(checkCusTrans);
    	
			TransactionHistoryDTO poBuyTrans = TransactionHistoryDTO.builder()
														.amount(check.getTotalRentPriceProduct())
														.transactionType("Thuê")
														.description("Hoàn trả tiền hóa đơn thuê:  -" + totalRentPriceFormarted + " VND. "
																+ "Hoàn trả tiền cọc hóa đơn : -" + cocMoneyFormarted + " VND")
														.orderRentDTO(check)
														.accountDTO(check.getProductownerDTO().getAccountDTO())
														.build();
			TransactionHistoryDTO checkPOTrans = transService.createBuyTransactionHistoryReturnDTO(poBuyTrans);
			if(checkPOTrans == null) 
				throw new TransactionHistoryCreatedFailed();
    	
			listTrans.add(checkPOTrans);
			
			listOrderRent = renDetailService.getAllOrderDetailByOrderRentIDReturnDTO(orderRentID);
        	for (OrderRentDetailDTO x : listOrderRent) {
        		ProductDTO prod = x.getProductDTO();
        		prod.setStatus(ProductStatus.AVAILABLE);
        		listProduct.add(prod);
			}
			
			transRepo.saveAll(listTrans);
			productRepo.saveAll(listProduct);
		}
		
		check.setStatus(status);
		
		return OrderRentResponseEntity.fromOrderRentDTO(rentRepo.save(check));

		}
	
	public OrderRentResponseEntity updateOrderCode(int orderRentID, String orderCode) throws CrudException {
		OrderRentDTO check = rentRepo.findById(orderRentID).orElse(null);

		if(check == null) {
			throw new OrderBuyNotFoundFailed();
	}
		check.setOrderCode(orderCode);
		return OrderRentResponseEntity.fromOrderRentDTO(rentRepo.save(check));
	}
	
	// ================================== PO ===============================================================================================================
	public List<OrderRentResponseEntity> getAll3StatusOrderRentByProductOwnerID(int productOwnerID) throws CrudException{
		ProductOwnerDTO check = poRepo.findById(productOwnerID).orElse(null);
		if(check == null) 
			throw new PendingMoneyNegative("ProductOwner not found");
		
		 return OrderRentResponseEntity.fromListOrderRentDTO(rentRepo.findAll3StatusOrderRentByProductOwnerID(productOwnerID));
	}
	
	// ================================== PREPARE =========================================================================
	public List<OrderRentResponseEntity> getAllPrepareOrderRentByProductOwnerID(int productOwnerID) throws CrudException{
		ProductOwnerDTO check = poRepo.findById(productOwnerID).orElse(null);
		if(check == null) 
			throw new PendingMoneyNegative("ProductOwner not found");
		
		 return OrderRentResponseEntity.fromListOrderRentDTO(rentRepo.findAllPrepareOrderRentByProductOwnerID(productOwnerID));
	}

	// ================================== COMPELTE =========================================================================
	public List<OrderRentResponseEntity> getAllCompletedOrderRentByProductOwnerID(int productOwnerID) throws CrudException {
		ProductOwnerDTO check = poRepo.findById(productOwnerID).orElse(null);
		if(check == null)
			throw new PendingMoneyNegative("ProductOwner not found");
		return OrderRentResponseEntity.fromListOrderRentDTO(rentRepo.findAllCompletedOrderRentByProductOwnerID(productOwnerID));
	}
	
	// ================================== PENDING =========================================================================
	public List<OrderRentResponseEntity> getAllPendingOrderRentByProductOwnerID(int productOwnerID) throws CrudException {
		ProductOwnerDTO check = poRepo.findById(productOwnerID).orElse(null);
		if(check == null)
			throw new PendingMoneyNegative("ProductOwner not found");
		return OrderRentResponseEntity.fromListOrderRentDTO(rentRepo.findAllPendingOrderRentByProductOwnerID(productOwnerID));
	}
	// ================================== DELIVERY =========================================================================
	public List<OrderRentResponseEntity> getAllDeliveryOrderRentByProductOwnerID(int productOwnerID) throws CrudException {
		ProductOwnerDTO check = poRepo.findById(productOwnerID).orElse(null);
		if(check == null)
			throw new PendingMoneyNegative("ProductOwner not found");
		return OrderRentResponseEntity.fromListOrderRentDTO(rentRepo.findAllDeliveryOrderRentByProductOwnerID(productOwnerID));
	}
	// ================================== CONFIRMING =========================================================================
	public List<OrderRentResponseEntity> getAllConfirmingOrderRentByProductOwnerID(int productOwnerID) throws CrudException {
		ProductOwnerDTO check = poRepo.findById(productOwnerID).orElse(null);
		if(check == null)
			throw new PendingMoneyNegative("ProductOwner not found");
		return OrderRentResponseEntity.fromListOrderRentDTO(rentRepo.findAllConfirmingOrderRentByProductOwnerID(productOwnerID));
	}
	// ================================== RENTING =========================================================================
	public List<OrderRentResponseEntity> getAllRentingOrderRentByProductOwnerID(int productOwnerID) throws CrudException {
		ProductOwnerDTO check = poRepo.findById(productOwnerID).orElse(null);
		if(check == null)
			throw new PendingMoneyNegative("ProductOwner not found");
		return OrderRentResponseEntity.fromListOrderRentDTO(rentRepo.findAllRentingOrderRentByProductOwnerID(productOwnerID));
	}
	
	// ================================== RETURNING =========================================================================Returning
	public List<OrderRentResponseEntity> getAllReturningOrderRentByProductOwnerID(int productOwnerID) throws CrudException {
		ProductOwnerDTO check = poRepo.findById(productOwnerID).orElse(null);
		if(check == null)
			throw new PendingMoneyNegative("ProductOwner not found");
		return OrderRentResponseEntity.fromListOrderRentDTO(rentRepo.findAllReturningOrderRentByProductOwnerID(productOwnerID));
	}
	// ================================== REJECTING =========================================================================
	
	public List<OrderRentResponseEntity> getAllRejectingOrderRentByProductOwnerID(int productOwnerID) throws CrudException {
		ProductOwnerDTO check = poRepo.findById(productOwnerID).orElse(null);
		if(check == null)
			throw new PendingMoneyNegative("ProductOwner not found");
		return OrderRentResponseEntity.fromListOrderRentDTO(rentRepo.findAllRejectingOrderRentByProductOwnerID(productOwnerID));
	}
	// ================================== REJECTING_COMPLETED =========================================================================
	public List<OrderRentResponseEntity> getAllRejectingCompletedOrderRentByProductOwnerID(int productOwnerID) throws CrudException {
		ProductOwnerDTO check = poRepo.findById(productOwnerID).orElse(null);
		if(check == null)
			throw new PendingMoneyNegative("ProductOwner not found");
		return OrderRentResponseEntity.fromListOrderRentDTO(rentRepo.findAllRejectingCompletedOrderRentByProductOwnerID(productOwnerID));
	}
	// ================================== CANCELED =========================================================================
	public List<OrderRentResponseEntity> getAllCanceledOrderRentByProductOwnerID(int productOwnerID) throws CrudException {
		ProductOwnerDTO check = poRepo.findById(productOwnerID).orElse(null);
		if(check == null)
			throw new PendingMoneyNegative("ProductOwner not found");
		return OrderRentResponseEntity.fromListOrderRentDTO(rentRepo.findAllCanceledOrderRentByProductOwnerID(productOwnerID));
	}
	
	
	// ================================== Customer ===============================================================================================================
	
	// ================================== PREPARE =========================================================================
	public List<OrderRentResponseEntity> getAllPrepareOrderRentByCustomerID(int customerID) throws CrudException {
		CustomerDTO check = cusRepo.findById(customerID).orElse(null);
		if(check == null)
			throw new PendingMoneyNegative("Customer not found");
		return OrderRentResponseEntity.fromListOrderRentDTO( rentRepo.findAllPrepareOrderRentByCustomerID(customerID));
	}
	
	// ================================== Completed =========================================================================
	public List<OrderRentResponseEntity> getAllCompletedOrderRentByCustomerID(int customerID) throws CrudException {
		CustomerDTO check = cusRepo.findById(customerID).orElse(null);
		if(check == null)
			throw new PendingMoneyNegative("Customer not found");
		
		return OrderRentResponseEntity.fromListOrderRentDTO(rentRepo.findAllCompletedOrderRentByCustomerID(customerID));
	}
	
	// ================================== PENDING =========================================================================
	public List<OrderRentResponseEntity> getAllPendingOrderRentByCustomerID(int customerID) throws CrudException {
		CustomerDTO check = cusRepo.findById(customerID).orElse(null);
		if(check == null)
			throw new PendingMoneyNegative("Customer not found");
		
		return OrderRentResponseEntity.fromListOrderRentDTO(rentRepo.findAllPendingOrderRentByCustomerID(customerID));
	}
	
	// ================================== DELIVERY =========================================================================
	public List<OrderRentResponseEntity> getAllDeliveryOrderRentByCustomerID(int customerID) throws CrudException {
		CustomerDTO check = cusRepo.findById(customerID).orElse(null);
		if(check == null)
			throw new PendingMoneyNegative("Customer not found");
		
		return OrderRentResponseEntity.fromListOrderRentDTO(rentRepo.findAllDeliveryOrderRentByCustomerID(customerID));
	}
	
	// ================================== CONFIRMING =========================================================================
	public List<OrderRentResponseEntity> getAllConfirmingOrderRentByCustomerID(int customerID) throws CrudException {
		CustomerDTO check = cusRepo.findById(customerID).orElse(null);
		if(check == null)
			throw new PendingMoneyNegative("Customer not found");
		
		return OrderRentResponseEntity.fromListOrderRentDTO(rentRepo.findAllConfirmingOrderRentByCustomerID(customerID));
	}
	
	// ================================== RENTING =========================================================================
	public List<OrderRentResponseEntity> getAllRentingOrderRentByCustomerID(int customerID) throws CrudException {
		CustomerDTO check = cusRepo.findById(customerID).orElse(null);
		if(check == null)
			throw new PendingMoneyNegative("Customer not found");
		
		return OrderRentResponseEntity.fromListOrderRentDTO(rentRepo.findAllRentingOrderRentByCustomerID(customerID));
	}
	
	// ================================== RETURNING =========================================================================
	public List<OrderRentResponseEntity> getAllReturningOrderRentByCustomerID(int customerID) throws CrudException {
		CustomerDTO check = cusRepo.findById(customerID).orElse(null);
		if(check == null)
			throw new PendingMoneyNegative("Customer not found");
		
		return OrderRentResponseEntity.fromListOrderRentDTO(rentRepo.findAllReturningOrderRentByCustomerID(customerID));
	}
	
	// ================================== REJECTING =========================================================================
	public List<OrderRentResponseEntity> getAllRejectingOrderRentByCustomerID(int customerID) throws CrudException {
		CustomerDTO check = cusRepo.findById(customerID).orElse(null);
		if(check == null)
			throw new PendingMoneyNegative("Customer not found");
		
		return OrderRentResponseEntity.fromListOrderRentDTO(rentRepo.findAllRejectingOrderRentByCustomerID(customerID));
	}
	
	// ================================== REJECTING_COMPLETED =========================================================================
	public List<OrderRentResponseEntity> getAllRejectingCompletedOrderRentByCustomerID(int customerID) throws CrudException {
		CustomerDTO check = cusRepo.findById(customerID).orElse(null);
		if(check == null)
			throw new PendingMoneyNegative("Customer not found");
		
		return OrderRentResponseEntity.fromListOrderRentDTO(rentRepo.findAllRejectingCompletedOrderRentByCustomerID(customerID));
	}
	
	// ================================== CANCELED =========================================================================
	public List<OrderRentResponseEntity> getAllCanceledOrderRentByCustomerID(int customerID) throws CrudException {
		CustomerDTO check = cusRepo.findById(customerID).orElse(null);
		if(check == null)
			throw new PendingMoneyNegative("Customer not found");
		
		return OrderRentResponseEntity.fromListOrderRentDTO(rentRepo.findAllCanceledCompletedOrderRentByCustomerID(customerID));
	}
	
	
	
	
	
	
}