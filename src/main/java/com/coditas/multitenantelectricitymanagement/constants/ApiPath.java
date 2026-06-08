package com.coditas.multitenantelectricitymanagement.constants;

public final class ApiPath {

    private ApiPath(){}

    public static final String BASE_PATH = "/api/v1";
    public static final String LOGIN = "/login";


    public static class Task {
        private Task(){}

        public static final String SALES_TASK = "/sales-task";
    }

    public static class State {
        private State(){}

        public static final String BASE = BASE_PATH+"/states";
        public static final String ID = "/{id}";
    }

    public static class SalesTeam{
        private SalesTeam(){}
        public static final String BASE = "/sales-team";
    }

    public static class StateHead{
        private StateHead(){}
        public static final String STATE_HEAD = "/state-head";
    }

    public static class SuperAdmin{
        private SuperAdmin() {
        }

        public static final String ONBOARD_MANAGEMENT_TEAM_MEMBER = "/onboard-management-team-member";
        public static final String ONBOARD_SALES_TEAM_MEMBER = "/onboard-sales-team-member";
        public static final String ONBOARD_SUPER_ADMIN = "/onboard-superadmin";
    }



}
