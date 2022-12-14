package org.source.builder;

import org.fr2eman.send.DataRequest;

import java.util.HashMap;
import java.util.Map;

public class BuilderRequest implements Builder {

    private int m_typeRequest;
    private Map<String, String> m_dataRequest;

    public BuilderRequest() {
        m_typeRequest = 0;
        m_dataRequest = new HashMap<String, String>();
    }

    public BuilderRequest(int typeRequest) {
        m_typeRequest = typeRequest;
        m_dataRequest = new HashMap<String, String>();
    }

    public void setTypeRequest(int typeRequest) {
        m_typeRequest = typeRequest;
        return;
    }

    public int getTypeRequest() {
        return m_typeRequest;
    }

    public void addRequestDatas(String key, String value) {
        m_dataRequest.put(key, value);
        return;
    }

    @Override
    public DataRequest buildRequest() {
        return new DataRequest(m_typeRequest, m_dataRequest);
    }
}
