package com.volvo.project.enums;

   public enum PathToSystemLogs {

        SYSTEM_LOGS_QA("pathToQA/SystemLogs/Systemlog.log"),
        VN("VN (Volvo Trucks)");

        private final String value;

        private PathToSystemLogs(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return value;
        }
    }

