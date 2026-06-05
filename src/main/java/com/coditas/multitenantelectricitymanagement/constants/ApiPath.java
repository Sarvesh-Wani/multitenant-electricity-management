package com.coditas.multitenantelectricitymanagement.constants;

public final class ApiPath {

    private ApiPath(){}

    public static final String BASE_PATH = "api/v1";

    public static class SalesTeam{

        private SalesTeam(){}

        public static final String BASE = BASE_PATH + "/sales-team";
    }

    public static class SuperAdmin{
        private SuperAdmin() {
        }

        public static final String ONBOARD_MANAGEMENT_TEAM_MEMBER = "/onboard-management-team-member";
    }



}
