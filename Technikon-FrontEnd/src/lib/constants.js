// constants.js

export const PATHS = {
    HOME: '/',
    LOGIN: '/login',
    SIGNUP: '/signup',
    REPAIR: '/repair/:id',
    CREATE_REPAIR: '/create-repair',
    ADMIN: '/admin',
    OWNER: (tin) => `/owner/${tin}`,
  };
  
  export const ROLES = {
    ADMIN: 'ROLE_ADMIN',
    USER: 'ROLE_USER',
  };
  