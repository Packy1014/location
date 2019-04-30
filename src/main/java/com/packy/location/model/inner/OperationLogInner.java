package com.packy.location.model.inner;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class OperationLogInner {

    private Long requestId;

    private Long operatorId;

    private String operatorName;

    private LocalDateTime operationTime;

    private String operation;

    private String className;

    private String methodName;

    private String parameters;

    private Object result;

    public enum MODULE {
        LOCATION("location");


        private String moduleName;

        MODULE(String moduleName) {
            this.moduleName = moduleName;
        }

        @Override
        public String toString() {
            return moduleName;
        }
    }

    public enum OPERATION_TYPE {
        READ("Read"),
        CREATE("Create"),
        UPDATE("Update"),
        DELETE("Delete");

        private String desc;

        OPERATION_TYPE(String desc) {
            this.desc = desc;
        }

        @Override
        public String toString() {
            return this.desc;
        }
    }
}
