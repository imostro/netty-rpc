package xyz.imstro.rpc.potocol;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: MOSTRO
 */
@Data
public class CustomProtocol implements Serializable {

    private static final long serialVersionUID = 7630981098761367482L;

    private String className;

    private String methodName;

    private Class<?>[] parameters;

    private Object[] values;

    private Object result;
}
