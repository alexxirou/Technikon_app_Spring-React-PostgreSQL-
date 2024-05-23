// constants.js

export const PATHS = {
    HOME: '/',
    LOGIN: '/login',
    SIGNUP: '/signup',
    REPAIR: '/repair/:id',
    CREATE_REPAIR: '/create-repair',
    SHOW_REPAIRS: '/repairs/all-by-owner/:propertyOwnerId',
    REPAIR_DETAILS: '/repair/:propertyOwnerId/:repairId',
    ADMIN: '/admin',
    OWNER: (tin) => `/owner/${tin}`,
  };
  
  export const ROLES = {
    ADMIN: 'ROLE_ADMIN',
    USER: 'ROLE_USER',
  };
  