package com.example.bipl.biplpos.dao.ws;




//----------------------------------------------------
//
// Generated by www.easywsdl.com
// Version: 5.0.8.0
//
// Created by Quasar Development at 28/03/2017
//
//---------------------------------------------------


import org.ksoap2.HeaderProperty;
import org.ksoap2.serialization.*;
import org.ksoap2.transport.*;

import java.util.List;


public class PUVDebitCardStatusPortBinding
{
    interface PUVIWcfMethod
    {
        PUVExtendedSoapSerializationEnvelope CreateSoapEnvelope() throws Exception;

        Object ProcessResult(PUVExtendedSoapSerializationEnvelope __envelope, Object result) throws Exception;
    }

    String url="http://10.200.14.10:9080/BIWS/DebitCardStatus";

    int timeOut=60000;
    public List< HeaderProperty> httpHeaders;
    public boolean enableLogging;

    public PUVDebitCardStatusPortBinding(){}

    public PUVDebitCardStatusPortBinding(String url)
    {
        this.url = url;
    }

    public PUVDebitCardStatusPortBinding(String url,int timeOut)
    {
        this.url = url;
        this.timeOut=timeOut;
    }

    protected org.ksoap2.transport.Transport createTransport()
    {
        try
        {
            java.net.URI uri = new java.net.URI(url);
            if(uri.getScheme().equalsIgnoreCase("https"))
            {
                int port=uri.getPort()>0?uri.getPort():443;
                return new HttpsTransportSE(uri.getHost(), port, uri.getPath(), timeOut);
            }
            else
            {
                return new HttpTransportSE(url,timeOut);
            }

        }
        catch (java.net.URISyntaxException e)
        {
        }
        return null;
    }

    protected PUVExtendedSoapSerializationEnvelope createEnvelope()
    {
        PUVExtendedSoapSerializationEnvelope envelope= new PUVExtendedSoapSerializationEnvelope(PUVExtendedSoapSerializationEnvelope.VER11);
        return envelope;
    }

    protected List sendRequest(String methodName,PUVExtendedSoapSerializationEnvelope envelope,org.ksoap2.transport.Transport transport  )throws Exception
    {
        return transport.call(methodName, envelope, httpHeaders);
    }

    Object getResult(Class destObj,Object source,String resultName,PUVExtendedSoapSerializationEnvelope __envelope) throws Exception
    {
        if(source==null)
        {
            return null;
        }
        if(source instanceof SoapPrimitive)
        {
            SoapPrimitive soap =(SoapPrimitive)source;
            if(soap.getName().equals(resultName))
            {
                Object instance=__envelope.get(source,destObj,false);
                return instance;
            }
        }
        else
        {
            SoapObject soap = (SoapObject)source;
            if (soap.hasProperty(resultName))
            {
                Object j=soap.getProperty(resultName);
                if(j==null)
                {
                    return null;
                }
                Object instance=__envelope.get(j,destObj,false);
                return instance;
            }
            else if( soap.getName().equals(resultName)) {
                Object instance=__envelope.get(source,destObj,false);
                return instance;
            }
       }

       return null;
    }


    public PUVwsResponse getDebitCardStatus(final PUVwsParam paramter ) throws Exception
    {
        return (PUVwsResponse)execute(new PUVIWcfMethod()
        {
            @Override
            public PUVExtendedSoapSerializationEnvelope CreateSoapEnvelope(){
              PUVExtendedSoapSerializationEnvelope __envelope = createEnvelope();
                __envelope.addMapping("","paramter",new PUVwsParam().getClass());
                SoapObject __soapReq = new SoapObject("http://ws.bi.com/", "getDebitCardStatus");
                __envelope.setOutputSoapObject(__soapReq);

                PropertyInfo __info=null;
                __info = new PropertyInfo();
                __info.namespace="";
                __info.name="paramter";
                __info.type=PUVwsParam.class;
                __info.setValue(paramter!=null?paramter:SoapPrimitive.NullSkip);
                __soapReq.addProperty(__info);
                return __envelope;
            }

            @Override
            public Object ProcessResult(PUVExtendedSoapSerializationEnvelope __envelope,Object __result)throws Exception {
                return (PUVwsResponse)getResult(PUVwsResponse.class,__result,"return",__envelope);
            }
        },"");
    }


    protected Object execute(PUVIWcfMethod wcfMethod,String methodName) throws Exception
    {
        org.ksoap2.transport.Transport __httpTransport=createTransport();
        __httpTransport.debug=enableLogging;
        PUVExtendedSoapSerializationEnvelope __envelope=wcfMethod.CreateSoapEnvelope();
        try
        {
            sendRequest(methodName, __envelope, __httpTransport);

        }
        finally {
            if (__httpTransport.debug) {
                if (__httpTransport.requestDump != null) {
                    android.util.Log.i("requestDump",__httpTransport.requestDump);

                }
                if (__httpTransport.responseDump != null) {
                    android.util.Log.i("responseDump",__httpTransport.responseDump);
                }
            }
        }
        Object __retObj = __envelope.bodyIn;
        if (__retObj instanceof org.ksoap2.SoapFault){
            org.ksoap2.SoapFault __fault = (org.ksoap2.SoapFault)__retObj;
            throw convertToException(__fault,__envelope);
        }else{
            return wcfMethod.ProcessResult(__envelope,__retObj);
        }
    }


    Exception convertToException(org.ksoap2.SoapFault fault,PUVExtendedSoapSerializationEnvelope envelope)
    {

        return new Exception(fault.faultstring);
    }
}


