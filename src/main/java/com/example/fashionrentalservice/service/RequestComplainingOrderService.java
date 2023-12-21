package com.example.fashionrentalservice.service;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.fashionrentalservice.exception.PendingMoneyNegative;
import com.example.fashionrentalservice.exception.TransactionHistoryCreatedFailed;
import com.example.fashionrentalservice.exception.handlers.CrudException;
import com.example.fashionrentalservice.model.dto.account.ProductOwnerDTO;
import com.example.fashionrentalservice.model.dto.account.TransactionHistoryDTO;
import com.example.fashionrentalservice.model.dto.account.WalletDTO;
import com.example.fashionrentalservice.model.dto.order.OrderRentDTO;
import com.example.fashionrentalservice.model.dto.order.OrderRentDTO.OrderRentStatus;
import com.example.fashionrentalservice.model.dto.order.OrderRentDetailDTO;
import com.example.fashionrentalservice.model.dto.product.ProductDTO;
import com.example.fashionrentalservice.model.dto.product.RequestComplainingOrderDTO;
import com.example.fashionrentalservice.model.dto.product.StaffRequestedDTO;
import com.example.fashionrentalservice.model.dto.product.ProductDTO.ProductStatus;
import com.example.fashionrentalservice.model.dto.product.RequestComplainingOrderDTO.ComplainingOrderStatus;
import com.example.fashionrentalservice.model.request.RequestComplainingOrderRequestEntity;
import com.example.fashionrentalservice.model.response.RequestComplainingOrderResponseEntity;
import com.example.fashionrentalservice.repositories.OrderRentRepository;
import com.example.fashionrentalservice.repositories.ProductOwnerRepository;
import com.example.fashionrentalservice.repositories.ProductRepository;
import com.example.fashionrentalservice.repositories.RequestComplainingOrderRepository;
import com.example.fashionrentalservice.repositories.StaffRequestedRepository;
import com.example.fashionrentalservice.repositories.TransactionHistoryRepository;

@Service
public class RequestComplainingOrderService {

	@Autowired
	private RequestComplainingOrderRepository requestComRepo;
	
	@Autowired
	private OrderRentRepository rentRepo;
	
	@Autowired
	private ProductOwnerRepository poRepo;
	
	@Autowired
	private StaffRequestedRepository staffRequestedRepo;
	
	@Autowired
	private StaffRequestedService staffRequestedService;
	
	@Autowired
	private NotificationService notiService;
	
	@Autowired
	private WalletService walletService;
	
	@Autowired
	private OrderRentDetailService rentDetailService;
	
	@Autowired
	private TransactionHistoryService transService;
	
	@Autowired
	private TransactionHistoryRepository transRepo;
	
	@Autowired
	private ProductRepository productRepo;
	private static final String BLANK_PATTERN = "^\\S.*$";
	
//================================== Tạo mới RequestComplaining ========================================
    public RequestComplainingOrderResponseEntity createRequestComplaining(RequestComplainingOrderRequestEntity entity) throws CrudException{
    	OrderRentDTO checkRent = rentRepo.findById(entity.getOrderRentID()).orElse(null);
    	ProductOwnerDTO checkPo = poRepo.findById(entity.getProductownerID()).orElse(null);
    	
    	if(checkRent == null)
    		throw new PendingMoneyNegative("OrderRent Not Found!");
    	if(checkPo == null)
    		throw new PendingMoneyNegative("ProductOwner Not Found!");
    	if(!isValidStringNotBlank(entity.getDescription())) {
       		throw new PendingMoneyNegative("Description Cannot blank");
    	}
        RequestComplainingOrderDTO dto = RequestComplainingOrderDTO.builder()
                .description(entity.getDescription())
                .expectedCost(entity.getExpectedCost())
                .createdtDate(LocalDate.now())
                .staffResponse("")
                .status(ComplainingOrderStatus.APPROVING)
                .orderRentDTO(checkRent)
                .productownerDTO(checkPo)
                .build();
       
        return RequestComplainingOrderResponseEntity.fromRequestComplainingOrderDTO(requestComRepo.save(dto));
    }
    
  //================================== Update Request Complaining Status  ========================================   
    public RequestComplainingOrderResponseEntity updateRequestStatus(int requestID,ComplainingOrderStatus status, int staffID, String staffResponse) throws CrudException {
    	DecimalFormat decimalFormat = new DecimalFormat("#,###,###,###");
    	List<TransactionHistoryDTO> listTrans = new ArrayList<>();
    	List<OrderRentDetailDTO> listDetail = new ArrayList<>();
    	List<ProductDTO> listProduct = new ArrayList<>();
    	RequestComplainingOrderDTO dto = requestComRepo.findById(requestID).orElse(null);
    	if(dto == null)
    		throw new PendingMoneyNegative("Request Complaining Not Found!");
    	StaffRequestedDTO checkAprrovedOrNah = staffRequestedRepo.findRequestAddProduct(requestID);
    	if(checkAprrovedOrNah != null) {
    		throw new PendingMoneyNegative("Đơn yêu cầu hỗ trợ này đã duyệt rồi!");
    	}
    	
    	if(status == ComplainingOrderStatus.APPROVED) {
        	OrderRentDTO rentOrder = dto.getOrderRentDTO();
        	WalletDTO cusWallet = rentOrder.getCustomerDTO().getAccountDTO().getWalletDTO();
        	WalletDTO poWallet = dto.getProductownerDTO().getAccountDTO().getWalletDTO();
        	
        	WalletDTO check = null;
        	
        	double newCocMoney = rentOrder.getCocMoneyTotal() - dto.getExpectedCost();
        	double newBalance = rentOrder.getTotalRentPriceProduct() + dto.getExpectedCost();
        	
        	String newCocMoneyFormarted = decimalFormat.format(newCocMoney);
        	String expectedCostFormarted = decimalFormat.format(dto.getExpectedCost());
        	String totalRentPriceFormarted = decimalFormat.format(rentOrder.getTotalRentPriceProduct());
        	
        	rentOrder.setStatus(OrderRentStatus.COMPLETED);
    		listDetail = rentDetailService.getAllOrderDetailByOrderRentIDReturnDTO(rentOrder.getOrderRentID());
    		for (OrderRentDetailDTO x : listDetail) {
				ProductDTO p = x.getProductDTO();
				p.setStatus(ProductStatus.AVAILABLE);
				listProduct.add(p);
			}
    		
    		check = walletService.StaffUpdate(cusWallet, poWallet, dto.getExpectedCost(), rentOrder);
    		if(check != null) {
            	TransactionHistoryDTO cusRentTrans = TransactionHistoryDTO.builder()
														.amount(newCocMoney)
														.transactionType("Thuê")
														.description("Trả phí bồi thường: -" + expectedCostFormarted + " VND. "
																+ "Nhận lại tiền cọc +" + newCocMoneyFormarted + " VND")
														.orderRentDTO(rentOrder)
														.accountDTO(cusWallet.getAccountDTO())
														.build();      												
            	TransactionHistoryDTO checkCusTrans = transService.createBuyTransactionHistoryReturnDTO(cusRentTrans);
            	if(checkCusTrans == null) 
            		throw new TransactionHistoryCreatedFailed();
            	listTrans.add(checkCusTrans);

            	TransactionHistoryDTO poRentTrans = TransactionHistoryDTO.builder()
													.amount(newBalance)
													.transactionType("Thuê")
													.description("Nhận tiền hóa đơn thuê : +" + totalRentPriceFormarted + " VND. "
															+ "Nhận tiền bồi thường +" + expectedCostFormarted + " VND. "
															+ "Hoàn trả lại tiền cọc -" + newCocMoneyFormarted + " VND" )
													.orderRentDTO(rentOrder)
													.accountDTO(poWallet.getAccountDTO())
													.build();
            	TransactionHistoryDTO checkPoTrans = transService.createBuyTransactionHistoryReturnDTO(poRentTrans);
            	if(checkPoTrans == null) 
            		throw new TransactionHistoryCreatedFailed();
            	
            	listTrans.add(checkPoTrans);
            	
    		}
    		staffRequestedService.createComplaining(requestID, staffID);
    		rentRepo.save(rentOrder);
    		productRepo.saveAll(listProduct);
    		transRepo.saveAll(listTrans);
    		dto.setStaffResponse(staffResponse);
    	}
    	
    	if(status == ComplainingOrderStatus.NOT_APPROVED) {
    		OrderRentDTO rentDTO = dto.getOrderRentDTO();
    		rentDTO.setStatus(OrderRentStatus.PROGRESSING_FAILED);
    		rentRepo.save(rentDTO);
    		staffRequestedService.createComplaining(requestID, staffID);
    		dto.setStaffResponse(staffResponse);
    	}
    	
        dto.setStatus(status);
        notiService.pushNotification(dto.getProductownerDTO().getAccountDTO().getAccountID(), "Đơn hỗ trợ", "Đơn hàng thuê số: " + dto.getOrderRentDTO().getOrderRentID() + " đã được duyệt.");
        
        	return RequestComplainingOrderResponseEntity.fromRequestComplainingOrderDTO(requestComRepo.save(dto));
    }
        

//================================== Lay tat ca Request========================================
	public List<RequestComplainingOrderResponseEntity> getApprovingRequest() {
		return requestComRepo.findApprovingRequest().stream().map(RequestComplainingOrderResponseEntity::fromRequestComplainingOrderDTO).collect(Collectors.toList());

	}
	
	//------------------------------------------------------------------
	public List<RequestComplainingOrderResponseEntity> getRequestComplainingNotByOrderRentID(int orderRentID) {
		return requestComRepo.findRequestComplainingNotByOrderRentID(orderRentID).stream().map(RequestComplainingOrderResponseEntity::fromRequestComplainingOrderDTO).collect(Collectors.toList());


	}
//================================== Lay Request bởi ID========================================	
	public RequestComplainingOrderResponseEntity getRequestByID(int requestID) throws CrudException{
		RequestComplainingOrderDTO dto = requestComRepo.findById(requestID).orElse(null);
		if(dto==null) 
			throw new PendingMoneyNegative("Request Complaining Not Found");
		return RequestComplainingOrderResponseEntity.fromRequestComplainingOrderDTO(dto);
		}
//================================== Xóa Request bởi ID========================================
    public RequestComplainingOrderResponseEntity deleteExistedRequestComplaining(int requestID) throws CrudException {
    	RequestComplainingOrderDTO dto = requestComRepo.findById(requestID).orElse(null);
    	if(dto == null) 
			throw new PendingMoneyNegative("Request Complaining Not Found");
    	
    	requestComRepo.deleteById(requestID);
        return RequestComplainingOrderResponseEntity.fromRequestComplainingOrderDTO(dto);
    }
    private boolean isValidStringNotBlank(String string) {
        Pattern pattern = Pattern.compile(BLANK_PATTERN);
        Matcher matcher = pattern.matcher(string);
        return matcher.matches();
    }
    
	
}
	
	
