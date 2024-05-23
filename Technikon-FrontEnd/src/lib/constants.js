export const PATHS = {
  HOME: '/',
  LOGIN: '/login',
  SIGNUP: '/signup',
  CREATE_REPAIR: '/create-repair',
  SHOW_REPAIRS: (propertyOwnerId) => `/repairs/all-by-owner/${propertyOwnerId}`,
  REPAIR_DETAILS: (propertyRepairId) => `/repair/${propertyRepairId}`,
  PROPERTIES: (propertyOwnerId) => `/property/${propertyOwnerId}`,
  ADMIN: '/admin',
  OWNER: (tin) => `/owner/${tin}`,
  UPDATE_OWNER: (tin) => `/update-owner/${tin}`,
};

export const ROLES = {
  ADMIN: 'ROLE_ADMIN',
  USER: 'ROLE_USER',
};
