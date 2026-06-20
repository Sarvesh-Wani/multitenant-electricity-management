package com.coditas.multitenantelectricitymanagement.constants;

public final class ApiPath {

    private ApiPath() {
    }

    public static final String BASE_PATH = "/api/v1";
    public static final String LOGIN = "/login";
    public static final String ID = "/{id}";


    public static class Task {
        private Task() {
        }

        public static final String SALES_TASK = "/sales-task";
    }

    public static class LocalTechnician {
        private LocalTechnician() {
        }

        public static final String BASE = BASE_PATH + "/cities/{city-id}/localtechnicians";
        public static final String ID = "/{id}";
    }

    public static class Biller {
        private Biller() {
        }

        public static final String BASE = BASE_PATH + "/cities/billers";
        public static final String ID = "/{id}";
        public static final String CUSTOMER = BASE_PATH + "/customers";
        public static final String BILL = "/bills";
        public static final String QUERIES = "/queries";
    }

    public static class CRM {
        private CRM() {
        }

        public static final String BASE = BASE_PATH + "/cities/crm";
        public static final String ID = "/{id}";
    }

    public static class Customer {
        private Customer() {
        }

        public static final String BASE = BASE_PATH + "/areas/{area-id}/customers";
        public static final String ID = "/{id}";
        public static final String ASSIGNMENT = "/assign-service-providers";
    }


    public static class Area {
        private Area() {
        }

        public static final String BASE = BASE_PATH + "/cities/{city-id}/areas";
        public static final String ID = "/{area-id}";
        public static final String TECHNICIAN = "/technicians";
        public static final String BILLER = "/billers";
    }

    public static class State {
        private State() {
        }

        public static final String BASE = BASE_PATH + "/states";
        public static final String ID = "/{id}";
    }

    public static class District {
        private District() {
        }

        public static final String BASE = BASE_PATH + "/districts";
        public static final String ID = "/{id}";
    }

    public static class City {
        private City() {
        }

        public static final String BASE = BASE_PATH + "/cities";
        public static final String ID = "/{id}";
    }

    public static class StateHead {
        private StateHead() {
        }

        public static final String STATE_HEAD = BASE_PATH + "/state-head";
    }

    public static class DistrictHead {
        private DistrictHead() {
        }

        public static final String DISTRICT_HEAD = BASE_PATH + "/district-head";
    }

    public static class CityHead {
        private CityHead() {
        }

        public static final String CITY_HEAD = BASE_PATH + "/city-head";
    }

    public static class SalesTeam {
        private SalesTeam() {
        }

        public static final String SALES_TEAM = "/sales-team";
        public static final String BASE = BASE_PATH + SALES_TEAM;
        public static final String ID = "/{sid}";
        public static final String CLIENT_COMPANY = "/client-companies";
    }


    public static class SuperAdmin {
        private SuperAdmin() {
        }

        public static final String ONBOARD_MANAGEMENT_TEAM_MEMBER = "/onboard-management-team-member";
        public static final String ONBOARD_SALES_TEAM_MEMBER = "/onboard-sales-team-member";
        public static final String ONBOARD_SUPER_ADMIN = "/onboard-superadmin";
        public static final String MANAGEMENT = "/management-team";
        public static final String ID = "/{id}";
    }

    public static class Employee {
        private Employee() {
        }

        public static final String BASE = BASE_PATH + "/employees";
        public static final String SALES = "/sales";
        public static final String OPERATION_HEAD = "/operations-head";
        public static final String MANAGER1 = "/manager-1";
        public static final String MANAGER2 = "/manager-2";
        public static final String BPO = "/bpo";
        public static final String BPOSTATE = "/bpo-states";
    }

    public static class CustomerQuery {
        private CustomerQuery() {
        }

        public static final String BASE = "/customers/{customer-id}/queries";
        public static final String QUERIES = "/queries";
        public static final String ESCALATED_M1 = "/escalated-m1";
        public static final String ID = "/{id}";
        public static final String ESCALATED_M2 = "/escalated-m2";
        public static final String RESOLVED = "/resolved";

    }

    public static class MeterConfig {

        private MeterConfig() {
        }

        public static final String METER = "/meters";
    }


}
