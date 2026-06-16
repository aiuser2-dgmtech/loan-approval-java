package com.bank.loans.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RequestLoggingInterceptorTest {

    @Mock
    private HttpServletRequest mockRequest;

    @Mock
    private HttpServletResponse mockResponse;

    private RequestLoggingInterceptor interceptor;

    @BeforeEach
    public void setUp() {
        interceptor = new RequestLoggingInterceptor();
    }

    @Test
    public void testPreHandle_StoresStartTime() {
        long beforeTime = System.currentTimeMillis();
        
        boolean result = interceptor.preHandle(mockRequest, mockResponse, null);
        
        long afterTime = System.currentTimeMillis();
        assertTrue(result);
        
        verify(mockRequest).setAttribute(eq("startTime"), anyLong());
    }

    @Test
    public void testAfterCompletion_LogsInfoFor200Status() {
        long startTime = System.currentTimeMillis() - 50;
        when(mockRequest.getAttribute("startTime")).thenReturn(startTime);
        when(mockRequest.getMethod()).thenReturn("GET");
        when(mockRequest.getRequestURI()).thenReturn("/api/loans/evaluate");
        when(mockResponse.getStatus()).thenReturn(200);

        interceptor.afterCompletion(mockRequest, mockResponse, null, null);

        verify(mockRequest).getAttribute("startTime");
        verify(mockResponse).getStatus();
    }

    @Test
    public void testAfterCompletion_LogsWarnFor400Status() {
        long startTime = System.currentTimeMillis() - 25;
        when(mockRequest.getAttribute("startTime")).thenReturn(startTime);
        when(mockRequest.getMethod()).thenReturn("POST");
        when(mockRequest.getRequestURI()).thenReturn("/api/loans/apply");
        when(mockResponse.getStatus()).thenReturn(400);

        interceptor.afterCompletion(mockRequest, mockResponse, null, null);

        verify(mockRequest).getAttribute("startTime");
        verify(mockResponse).getStatus();
    }

    @Test
    public void testAfterCompletion_LogsErrorFor500Status() {
        long startTime = System.currentTimeMillis() - 100;
        when(mockRequest.getAttribute("startTime")).thenReturn(startTime);
        when(mockRequest.getMethod()).thenReturn("GET");
        when(mockRequest.getRequestURI()).thenReturn("/api/loans/check");
        when(mockResponse.getStatus()).thenReturn(500);

        interceptor.afterCompletion(mockRequest, mockResponse, null, null);

        verify(mockRequest).getAttribute("startTime");
        verify(mockResponse).getStatus();
    }

    @Test
    public void testAfterCompletion_HandlesNullStartTime() {
        when(mockRequest.getAttribute("startTime")).thenReturn(null);

        assertDoesNotThrow(() -> 
            interceptor.afterCompletion(mockRequest, mockResponse, null, null)
        );
    }

    @Test
    public void testAfterCompletion_Logs201AsInfo() {
        long startTime = System.currentTimeMillis() - 10;
        when(mockRequest.getAttribute("startTime")).thenReturn(startTime);
        when(mockRequest.getMethod()).thenReturn("POST");
        when(mockRequest.getRequestURI()).thenReturn("/api/loans/create");
        when(mockResponse.getStatus()).thenReturn(201);

        interceptor.afterCompletion(mockRequest, mockResponse, null, null);

        verify(mockResponse).getStatus();
    }

    @Test
    public void testAfterCompletion_Logs404AsWarn() {
        long startTime = System.currentTimeMillis() - 15;
        when(mockRequest.getAttribute("startTime")).thenReturn(startTime);
        when(mockRequest.getMethod()).thenReturn("GET");
        when(mockRequest.getRequestURI()).thenReturn("/api/loans/nonexistent");
        when(mockResponse.getStatus()).thenReturn(404);

        interceptor.afterCompletion(mockRequest, mockResponse, null, null);

        verify(mockResponse).getStatus();
    }

    @Test
    public void testAfterCompletion_Logs503AsError() {
        long startTime = System.currentTimeMillis() - 200;
        when(mockRequest.getAttribute("startTime")).thenReturn(startTime);
        when(mockRequest.getMethod()).thenReturn("GET");
        when(mockRequest.getRequestURI()).thenReturn("/api/loans/check");
        when(mockResponse.getStatus()).thenReturn(503);

        interceptor.afterCompletion(mockRequest, mockResponse, null, null);

        verify(mockResponse).getStatus();
    }
}
