package com.example.fashionrentalservice.service;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.fashionrentalservice.controller.AccountTransactionHistoryController;
import com.example.fashionrentalservice.exception.CreateOrderFailed;
import com.example.fashionrentalservice.exception.CusNotFoundByID;
import com.example.fashionrentalservice.exception.OrderBuyNotFoundFailed;
import com.example.fashionrentalservice.exception.PONotFoundByID;
import com.example.fashionrentalservice.exception.PendingMoneyNegative;
import com.example.fashionrentalservice.exception.ProductNotAvailable;
import com.example.fashionrentalservice.exception.ProductNotForRent;
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
import com.example.fashionrentalservice.model.dto.order.OrderBuyDTO;
import com.example.fashionrentalservice.model.dto.order.OrderBuyDTO.OrderBuyStatus;
import com.example.fashionrentalservice.model.dto.order.OrderRentDTO.OrderRentStatus;
import com.example.fashionrentalservice.model.dto.order.OrderBuyDetailDTO;
import com.example.fashionrentalservice.model.dto.order.OrderRentDTO;
import com.example.fashionrentalservice.model.dto.product.ProductDTO;
import com.example.fashionrentalservice.model.dto.product.ProductDTO.ProductStatus;
import com.example.fashionrentalservice.model.dto.product.ProductDTO.checkTypeSaleorRentorSaleRent;
import com.example.fashionrentalservice.model.dto.product.VoucherDTO;
import com.example.fashionrentalservice.model.request.OrderBuyDetailRequestEntity;
import com.example.fashionrentalservice.model.request.OrderBuyRequestEntity;
import com.example.fashionrentalservice.model.response.OrderBuyDetailResponseEntity;
import com.example.fashionrentalservice.model.response.OrderBuyResponseEntity;
import com.example.fashionrentalservice.model.response.OrderRentResponseEntity;
import com.example.fashionrentalservice.repositories.CustomerRepository;
import com.example.fashionrentalservice.repositories.OrderBuyDetailRepository;
import com.example.fashionrentalservice.repositories.OrderBuyRepository;
import com.example.fashionrentalservice.repositories.ProductOwnerRepository;
import com.example.fashionrentalservice.repositories.ProductRepository;
import com.example.fashionrentalservice.repositories.TransactionHistoryRepository;
import com.example.fashionrentalservice.repositories.VoucherRepository;
import com.example.fashionrentalservice.repositories.WalletRepository;

@Service
public class OrderBuyService {

	@Autowired
	private CustomerRepository cusRepo;
	
	@Autowired
	private ProductOwnerRepository poRepo;
	
	@Autowired
	private OrderBuyRepository buyRepo;
	
	@Autowired
	private OrderBuyDetailRepository buyDetailRepo;
	
	@Autowired
	private OrderBuyDetailService buyDetailService;
	
	@Autowired
	private ProductRepository productRepo;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private NotificationService notiService;
	
	@Autowired
	private TransactionHistoryService transService;
	
	@Autowired
	private TransactionHistoryRepository transRepo;
	
	@Autowired
	private WalletService walletService;
	
	@Autowired
	private WalletRepository walletRepo;
	
	@Autowired
	private VoucherRepository voRepo;




//================================== Tạo mới OrderBuy - ========================================
    public List<OrderBuyResponseEntity> createOrderBuy(List<OrderBuyRequestEntity> entity) throws CrudException{
        List<OrderBuyDTO> listOrder = new ArrayList<>();
        List<OrderBuyDetailDTO> listOrderDetail = new ArrayList<>();
        List<ProductDTO> listProduct = new ArrayList<>();
        List<WalletDTO> listWallet = new ArrayList<>();
        List<TransactionHistoryDTO> listTrans = new ArrayList<>();
        DecimalFormat decimalFormat = new DecimalFormat("#,###,###,###");
        
        for (OrderBuyRequestEntity x : entity) {
        	CustomerDTO cus = cusRepo.findById(x.getCustomerID()).orElse(null);
        	ProductOwnerDTO po = poRepo.findById(x.getProductownerID()).orElse(null);  	
        	VoucherDTO voucher = voRepo.findById(x.getVoucherID()).orElse(null);
        	if( cus == null)
        	    throw new CusNotFoundByID();
        	if( cus.getAccountDTO().getStatus() == AccountStatus.NOT_VERIFIED)
        		throw new PendingMoneyNegative("Your account is not verified!");
        	if( po == null)
        	    throw new PONotFoundByID();
        	if (cus.getAccountDTO().getWalletDTO() == null)
        		throw new WalletCusNotFound();
        	if (po.getAccountDTO().getWalletDTO() == null)
        		throw new WalletCusNotFound();      	
        	
        	OrderBuyDTO orderBuy = OrderBuyDTO.builder()
        							.total(x.getTotal())
        							.totalBuyPriceProduct(x.getTotalBuyPriceProduct())
        							.shippingfee(x.getShippingFee())
        							.dateOrder(LocalDateTime.now())
        							.status(OrderBuyStatus.PENDING)
        							.customerAddress(x.getCustomerAddress())
        							.customerDTO(cus)
        							.productownerDTO(po)
        							.voucherDTO(voucher)
        							.build();        	
        	for (OrderBuyDetailRequestEntity detail : x.getOrderDetail()) {
        		ProductDTO product = productRepo.findById(detail.getProductID()).orElse(null);
        		if(product == null)
        			throw new ProductNotFoundByID();
        		if (product.getStatus() == ProductDTO.ProductStatus.SOLD_OUT || product.getStatus() == ProductDTO.ProductStatus.RENTING) {
        			throw new ProductNotAvailable();
				}else if(product.getCheckType() == checkTypeSaleorRentorSaleRent.RENT)
					throw new ProductNotForRent();
        		
        		OrderBuyDetailDTO detailBuy = OrderBuyDetailDTO.builder()
        										.productDTO(product)
        										.orderBuyDTO(orderBuy)
        										.price(detail.getPrice())
        										.build();
        		listOrderDetail.add(detailBuy);
        		listProduct.add(detailBuy.getProductDTO());
			}
        	listOrder.add(orderBuy);
        }
            
        for (OrderBuyDTO x : listOrder) {
        	
        	WalletDTO checkCus = walletService.updateBalanceReturnDTO(x.getCustomerDTO().getAccountDTO().getWalletDTO().getWalletID(), x.getTotal());
        	if(checkCus == null) 
        		throw new WalletInOrderServiceFailed();
        	listWallet.add(checkCus);
        	
        	WalletDTO checkPo = walletService.updatePendingMoneyReturnDTO(x.getProductownerDTO().getAccountDTO().getWalletDTO().getWalletID(), x.getTotalBuyPriceProduct());
        	if(checkPo == null) 
        		throw new WalletInOrderServiceFailed();
        	listWallet.add(checkPo);
        	
        	String formatted = decimalFormat.format(x.getTotal());
        	String TotalBuyPriceformatted = decimalFormat.format(x.getTotalBuyPriceProduct());
        	TransactionHistoryDTO cusBuyTrans = TransactionHistoryDTO.builder()
        													.amount(x.getTotal())
        													.transactionType("Mua")
        													.description("Thanh toán hóa đơn: -" + formatted + " VND")
        													.orderBuyDTO(x)
        													.accountDTO(x.getCustomerDTO().getAccountDTO())
        													.build();      												
        	TransactionHistoryDTO checkCusTrans = transService.createBuyTransactionHistoryReturnDTO(cusBuyTrans);
        	if(checkCusTrans == null) 
        		throw new TransactionHistoryCreatedFailed();
        	listTrans.add(checkCusTrans);
        	
        	TransactionHistoryDTO poBuyTrans = TransactionHistoryDTO.builder()
															.amount(x.getTotalBuyPriceProduct())
															.transactionType("Mua")
															.description("Nhận tiền từ hóa đơn: +" + TotalBuyPriceformatted + " VND ")
															.orderBuyDTO(x)
															.accountDTO(x.getProductownerDTO().getAccountDTO())
															.build();
        	TransactionHistoryDTO checkPOTrans = transService.createBuyTransactionHistoryReturnDTO(poBuyTrans);
        	if(checkPOTrans == null) 
        		throw new TransactionHistoryCreatedFailed();
        	listTrans.add(checkPOTrans);
		}
        buyRepo.saveAll(listOrder);
        buyDetailRepo.saveAll(listOrderDetail);
		productService.updateListProductStatus(listProduct);
        walletRepo.saveAll(listWallet);
        transRepo.saveAll(listTrans);
        
        for (OrderBuyDTO x : listOrder) {
			notiService.pushNotification(x.getProductownerDTO().getAccountDTO().getAccountID(), "BÁN", "Bạn có đơn hàng BÁN mới từ : " + x.getCustomerDTO().getFullName());
		}
   
        try {
            return OrderBuyResponseEntity.fromListOrderBuyDTO(listOrder);
        } catch (Exception e) {
        	e.printStackTrace();
            throw new CreateOrderFailed();
        } 
    }
    
    
    /*-------------------------------------------------- Customer OrderBuy ------------------------------------------------------------------------------*/
    //================================== Lay tat ca Order by CustomerID================================================================
	public List<OrderBuyResponseEntity> getAllPendingOrderByCustomerID(int customerID) {
		return OrderBuyResponseEntity.fromListOrderBuyDTO(buyRepo.findAllPendingOrderBuyByCustomerID(customerID));
	}
	public List<OrderBuyResponseEntity> getAllOrderByCustomerID(int customerID) {
		return OrderBuyResponseEntity.fromListOrderBuyDTO(buyRepo.findAllOrderBuyByProductOwnerID(customerID));
	}
	
	
	//================================== Lay tat ca CANCELED OrderBUY by CustomerID================================================
	public List<OrderBuyResponseEntity> getAllCanceledOrderByCustomerID(int customerID) {
		return OrderBuyResponseEntity.fromListOrderBuyDTO(buyRepo.findAllCanceledOrderBuyByCustomerID(customerID));
	}
	
	//================================== Lay tat ca PREPARE OrderBUY by CustomerID=================================================
	public List<OrderBuyResponseEntity> getAllPrepareOrderByCustomerID(int customerID) {
		return OrderBuyResponseEntity.fromListOrderBuyDTO(buyRepo.findAllPrepareOrderBuyByCustomerID(customerID));
	}
	
	//================================== Lay tat ca REJECTING OrderBUY by CustomerID===============================================
	public List<OrderBuyResponseEntity> getAllRejectingOrderByCustomerID(int customerID) {
		return OrderBuyResponseEntity.fromListOrderBuyDTO(buyRepo.findAllRejectingOrderBuyByCustomerID(customerID));
	}
	
	//================================== Lay tat ca REJECTING_COMPLETED OrderBUY by CustomerID=====================================
	public List<OrderBuyResponseEntity> getAllRejectingCompletedOrderByCustomerID(int customerID) {
		return OrderBuyResponseEntity.fromListOrderBuyDTO(buyRepo.findAllRejectingCompletedOrderBuyByCustomerID(customerID));
	}
	
	//================================== Lay tat ca COMPLETED OrderBUY by CustomerID=====================================
	public List<OrderBuyResponseEntity> getAllCompletedOrderByCustomerID(int customerID) {
		return OrderBuyResponseEntity.fromListOrderBuyDTO(buyRepo.findAllCompletedOrderBuyByCustomerID(customerID));
	}
	
	//================================== Lay tat ca CONFIRMING OrderBUY by CustomerID==============================================
	public List<OrderBuyResponseEntity> getAllConfirmingOrderByCustomerID(int customerID) {
		return OrderBuyResponseEntity.fromListOrderBuyDTO(buyRepo.findAllConfirmingOrderBuyByCustomerID(customerID));
	}
	
	//================================== Lay tat ca READY_PICKUP OrderBUY by CustomerID============================================
	public List<OrderBuyResponseEntity> getAllDeliveryOrderCustomerID(int customerID) {
		return OrderBuyResponseEntity.fromListOrderBuyDTO(buyRepo.findAllDeliveryOrderBuyByCustomerID(customerID));
	}
	
	
	
	
	
	
	/*-------------------------------------------------- Productowner OrderBuy ------------------------------------------------------------------------------*/
	public List<OrderBuyResponseEntity> getAll3StatusOrderByProductOwnerID(int productownerID) {
		return OrderBuyResponseEntity.fromListOrderBuyDTO(buyRepo.findAll3StatusOrderBuyByProductOwnerID(productownerID));
	}
	
	public List<OrderBuyResponseEntity> getAllRejectingAndRejectingCompletedOrderByProductOwnerID(int productownerID) {
		return OrderBuyResponseEntity.fromListOrderBuyDTO(buyRepo.findRejectingAndRejectingCompletedOrderByProductOwnerID(productownerID));
	}
	
	
	//================================== Lay tat ca PENDING OrderBUY by ProductOwnerID=================================================
	public List<OrderBuyResponseEntity> getAllPendingOrderByProductOwnerID(int productownerID) {
		return OrderBuyResponseEntity.fromListOrderBuyDTO(buyRepo.findAllPendingOrderBuyByProductOwnerID(productownerID));
	}
	
	//================================== Lay tat ca CANCELED OrderBUY by ProductOwnerID================================================
	public List<OrderBuyResponseEntity> getAllCanceledOrderByProductOwnerID(int productownerID) {
		return OrderBuyResponseEntity.fromListOrderBuyDTO(buyRepo.findAllCanceledOrderBuyByProductOwnerID(productownerID));
	}
	
	//================================== Lay tat ca PREPARE OrderBUY by ProductOwnerID=================================================
	public List<OrderBuyResponseEntity> getAllPrepareOrderByProductOwnerID(int productownerID) {
		return OrderBuyResponseEntity.fromListOrderBuyDTO(buyRepo.findAllPrepareOrderBuyByProductOwnerID(productownerID));
	}
	
	//================================== Lay tat ca REJECTING OrderBUY by ProductOwnerID===============================================
	public List<OrderBuyResponseEntity> getAllRejectingOrderByProductOwnerID(int productownerID) {
		return OrderBuyResponseEntity.fromListOrderBuyDTO(buyRepo.findAllRejectingOrderBuyByProductOwnerID(productownerID));
	}
	
	//================================== Lay tat ca REJECTING_COMPLETED OrderBUY by ProductOwnerID=====================================
	public List<OrderBuyResponseEntity> getAllRejectingCompletedOrderByProductOwnerID(int productownerID) {
		return OrderBuyResponseEntity.fromListOrderBuyDTO(buyRepo.findAllRejectingCompletedOrderBuyByProductOwnerID(productownerID));
	}
	
	//================================== Lay tat ca COMPLETED OrderBUY by ProductOwnerID=====================================
	public List<OrderBuyResponseEntity> getAllCompletedOrderByProductOwnerID(int productownerID) {
		return OrderBuyResponseEntity.fromListOrderBuyDTO(buyRepo.findAllCompletedOrderBuyByProductOwnerID(productownerID));
	}
	
//	public List<OrderBuyResponseEntity> getAllByOrderBuyID(int orderbuyID) throws CrudException {
//		OrderBuyDTO check = buyRepo.findById(orderbuyID).orElse(null);
//		if(check == null)
//			throw new PendingMoneyNegative("Rent ID not found");
//		return OrderBuyResponseEntity.fromListOrderBuyDTO(buyRepo.findAllByOrderbuyID(orderbuyID));
//	}
	public OrderBuyResponseEntity getAllByOrderBuyID(int orderbuyID) throws CrudException {
		OrderBuyDTO check = buyRepo.findById(orderbuyID).orElse(null);
		if(check == null)
			throw new PendingMoneyNegative("Rent ID not found");
		return OrderBuyResponseEntity.fromOrderBuyDTO(buyRepo.findAllByOrderbuyID(orderbuyID));
	}
	
	//================================== Lay tat ca CONFIRMING OrderBUY by ProductOwnerID==============================================
	public List<OrderBuyResponseEntity> getAllConfirmingOrderByProductOwnerID(int productownerID) {
		return OrderBuyResponseEntity.fromListOrderBuyDTO(buyRepo.findAllConfirmingOrderBuyByProductOwnerID(productownerID));
	}
	
	//================================== Lay tat ca READY_PICKUP OrderBUY by ProductOwnerID============================================
	public List<OrderBuyResponseEntity> getDeliveryOrderByProductOwnerID(int productownerID) {
		return OrderBuyResponseEntity.fromListOrderBuyDTO(buyRepo.findAllDeliveryOrderBuyByProductOwnerID(productownerID));
	}
	
	//================================== Lay tat ca OrderBuy ==========================================================================
	public List<OrderBuyResponseEntity> getAllOrder() {
		return OrderBuyResponseEntity.fromListOrderBuyDTO(buyRepo.findAll());
	}
	
	//================================== Lay tat ca OrderBuy ==========================================================================
	public List<OrderBuyResponseEntity> getAllCanceledOrderBuy() {
		return OrderBuyResponseEntity.fromListOrderBuyDTO(buyRepo.findAllCanceledOrderBuy());
	}
	
	//================================== Lay tat ca Order trong 1 thang by ProductOwnerID =============================================
	public List<OrderBuyResponseEntity> getTotal1MonthOrderByProductOwnerID(int productOwnerID) {
		LocalDateTime startDate = LocalDateTime.now().minusMonths(1);
		List<OrderBuyDTO> list = buyRepo.getTotal1MonthByProductOwnerID(productOwnerID, startDate);
		return  OrderBuyResponseEntity.fromListOrderBuyDTO(list);
	}
	
	//================================== Lay tat ca Order trong 1 thang by ProductOwnerID ========================================
	public List<OrderBuyResponseEntity> getTotal1WeekOrderByProductOwnerID(int productOwnerID) {
		LocalDateTime startDate = LocalDateTime.now().minusWeeks(1);
		List<OrderBuyDTO> list = buyRepo.getTotal1WeekByProductOwnerID(productOwnerID, startDate);
		return  OrderBuyResponseEntity.fromListOrderBuyDTO(list);
	}
	
	//================================== UpdateOrderStatus .  ========================================
	public OrderBuyResponseEntity updateOrderBuyByOrderBuyID(int orderBuyID, OrderBuyStatus status) throws CrudException {
		OrderBuyDTO check = buyRepo.findById(orderBuyID).orElse(null);
		DecimalFormat decimalFormat = new DecimalFormat("#,###,###,###");
		List<TransactionHistoryDTO> listTrans = new ArrayList<>();
		List<ProductDTO> listProduct = new ArrayList<>();
		
		
		if(check == null) 
			throw new OrderBuyNotFoundFailed();
		
		WalletDTO checkWalletPO = check.getProductownerDTO().getAccountDTO().getWalletDTO();
		WalletDTO checkWalletCus = check.getCustomerDTO().getAccountDTO().getWalletDTO();
		if(checkWalletPO == null) 
			throw new WalletPoNotFound();
		if(checkWalletCus == null) 
			throw new WalletCusNotFound();
		
		
		//Confirm status
				if(status== OrderBuyStatus.CONFIRMING) {
					notiService.pushNotification(check.getCustomerDTO().getAccountDTO().getAccountID(), "Mua", "Đơn hàng mã : " + check.getOrderBuyID() +" đang chờ bạn xác nhận");
				}
		
		if(status == OrderBuyStatus.COMPLETED) { 
			walletService.updatePendingMoneyToBalanceReturnDTO(checkWalletPO.getWalletID(), check.getTotalBuyPriceProduct());
			String TotalBuyPriceformatted = decimalFormat.format(check.getTotalBuyPriceProduct());	
			TransactionHistoryDTO poBuyTrans = TransactionHistoryDTO.builder()
														.amount(check.getTotalBuyPriceProduct())
														.transactionType("Mua")
														.description("Hoàn tất đơn hàng : +" + TotalBuyPriceformatted + " VND vào số dư ví.")
														.orderBuyDTO(check)
														.accountDTO(check.getProductownerDTO().getAccountDTO())
														.build();
			TransactionHistoryDTO checkPOTrans = transService.createBuyTransactionHistoryReturnDTO(poBuyTrans);
			if(checkPOTrans == null) 
				throw new TransactionHistoryCreatedFailed();	
			transRepo.save(checkPOTrans);
			notiService.pushNotification(check.getProductownerDTO().getAccountDTO().getAccountID(), "Bán", "Đơn hàng mã : " + check.getOrderBuyID()+" đã thành công");
		}
		if(status == OrderBuyStatus.CANCELED) { 
			walletService.updatePendingMoneyToCustomerBalanceReturnDTO(checkWalletCus , checkWalletPO, check.getTotal());
			
        	String totalformatted = decimalFormat.format(check.getTotal());
        	String totalBuyPriceformatted = decimalFormat.format(check.getTotalBuyPriceProduct());
        	TransactionHistoryDTO cusBuyTrans = TransactionHistoryDTO.builder()
        													.amount(check.getTotal())
        													.transactionType("Mua")
        													.description("Hoàn tiền hóa đơn: +" + totalformatted + " VND")
        													.orderBuyDTO(check)
        													.accountDTO(check.getCustomerDTO().getAccountDTO())
        													.build();      												
        	TransactionHistoryDTO checkCusTrans = transService.createBuyTransactionHistoryReturnDTO(cusBuyTrans);
        	if(checkCusTrans == null) 
        		throw new TransactionHistoryCreatedFailed();
        	listTrans.add(checkCusTrans);
        	
        	TransactionHistoryDTO poBuyTrans = TransactionHistoryDTO.builder()
															.amount(check.getTotalBuyPriceProduct())
															.transactionType("Mua")
															.description("Hoàn trả tiền đơn hàng : -" + totalBuyPriceformatted + " VND ")
															.orderBuyDTO(check)
															.accountDTO(check.getProductownerDTO().getAccountDTO())
															.build();
        	TransactionHistoryDTO checkPOTrans = transService.createBuyTransactionHistoryReturnDTO(poBuyTrans);
        	if(checkPOTrans == null) 
        		throw new TransactionHistoryCreatedFailed();
        	listTrans.add(checkPOTrans);
    
			List<OrderBuyDetailDTO> list = buyDetailService.getAllOrderDetailByOrderBuyIDReturnDTO(orderBuyID);
				for (OrderBuyDetailDTO x : list) {
					ProductDTO productt = x.getProductDTO();
					productt.setStatus(ProductStatus.AVAILABLE);
					listProduct.add(productt);
					}	
			transRepo.saveAll(listTrans);
			productRepo.saveAll(listProduct);		
			notiService.pushNotification(check.getProductownerDTO().getAccountDTO().getAccountID(), "Bán", "Đơn hàng mã : " + check.getOrderBuyID()+" đã hủy");			
		}
		if(status == OrderBuyStatus.REJECTING_COMPLETED) { 
			walletService.updatePendingMoneyToCustomerBalanceReturnDTO(checkWalletCus , checkWalletPO, check.getTotalBuyPriceProduct());
		
        	String TotalBuyPriceformatted = decimalFormat.format(check.getTotalBuyPriceProduct());
        	TransactionHistoryDTO cusBuyTrans = TransactionHistoryDTO.builder()
        													.amount(check.getTotalBuyPriceProduct())
        													.transactionType("Mua")
        													.description("Hoàn tiền hóa đơn: +" + TotalBuyPriceformatted + " VND")
        													.orderBuyDTO(check)
        													.accountDTO(check.getCustomerDTO().getAccountDTO())
        													.build();      												
        	TransactionHistoryDTO checkCusTrans = transService.createBuyTransactionHistoryReturnDTO(cusBuyTrans);
        	if(checkCusTrans == null) 
        		throw new TransactionHistoryCreatedFailed();
        	listTrans.add(checkCusTrans);
        	
        	TransactionHistoryDTO poBuyTrans = TransactionHistoryDTO.builder()
															.amount(check.getTotalBuyPriceProduct())
															.transactionType("Mua")
															.description("Hoàn trả tiền đơn hàng : -" + TotalBuyPriceformatted + " VND ")
															.orderBuyDTO(check)
															.accountDTO(check.getProductownerDTO().getAccountDTO())
															.build();
        	TransactionHistoryDTO checkPOTrans = transService.createBuyTransactionHistoryReturnDTO(poBuyTrans);
        	if(checkPOTrans == null) 
        		throw new TransactionHistoryCreatedFailed();
        	listTrans.add(checkPOTrans);
    
			List<OrderBuyDetailDTO> list = buyDetailService.getAllOrderDetailByOrderBuyIDReturnDTO(orderBuyID);
				for (OrderBuyDetailDTO x : list) {
					ProductDTO productt = x.getProductDTO();
					productt.setStatus(ProductStatus.AVAILABLE);
					listProduct.add(productt);
					}	
			transRepo.saveAll(listTrans);
			productRepo.saveAll(listProduct);
			notiService.pushNotification(check.getCustomerDTO().getAccountDTO().getAccountID(), "Thuê", "Đơn hàng mã : " + check.getOrderBuyID()+" đã được chủ sản phẩm đồng ý hoàn trả!");

		}	
		
		check.setStatus(status);
		
		return  OrderBuyResponseEntity.fromOrderBuyDTO(buyRepo.save(check));
	}
	public OrderBuyResponseEntity updateOrderCode(int orderBuyID, String orderCode) throws CrudException {
		OrderBuyDTO check = buyRepo.findById(orderBuyID).orElse(null);

		if(check == null) {
			throw new OrderBuyNotFoundFailed();
	}
		check.setOrderCode(orderCode);
		return OrderBuyResponseEntity.fromOrderBuyDTO(buyRepo.save(check));
	}
	
	
}
	
	
