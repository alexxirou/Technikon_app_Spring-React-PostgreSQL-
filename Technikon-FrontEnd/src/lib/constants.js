export const PATHS = {
  HOME: '/',
  LOGIN: '/login',
  SIGNUP: '/signup',
  CREATE_REPAIR: '/create-repair',
  SHOW_REPAIRS: (propertyOwnerId) => `/repairs/all-by-owner/${propertyOwnerId}`,
  REPAIR_DETAILS: (propertyOwnerId,propertyRepairId) => `/repair/${propertyOwnerId}/${propertyRepairId}`,
  PROPERTIES: (propertyOwnerId) => `/property/${propertyOwnerId}`,
  ADMIN: (id) => `/admin/${id}`,
  OWNER: (id) => `/owner/${id}`,
  UPDATE_OWNER: (id) => `/update-owner/${id}`,
};

export const ROLES = {
  ADMIN: 'ROLE_ADMIN',
  USER: 'ROLE_USER',
};
