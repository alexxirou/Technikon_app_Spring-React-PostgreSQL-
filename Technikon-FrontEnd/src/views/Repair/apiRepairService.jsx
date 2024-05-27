import api from '../../api/Api';

export const createRepair = async (propertyOwnerId, repairData) => {
    const response = await api.post('/api/property-repairs/create', repairData);
    return response.data;
}

export const fetchRepairs = async (propertyOwnerId) => {
  const response = await api.get(`/api/property-repairs/all-by-owner/${propertyOwnerId}`);
  return response.data;
};

export const searchRepairsByDate = async (propertyOwnerId, date) => {
  const response = await api.get(`/api/property-repairs/${propertyOwnerId}/get-by-date/${date}`);
  return response.data;
};

export const searchRepairsByDateRange = async (propertyOwnerId, startDate, endDate) => {
  const response = await api.get(`/api/property-repairs/${propertyOwnerId}/get-by-dates/${startDate}/${endDate}`);
  return response.data;
};

export const updateRepair = async (propertyOwnerId, repairId, repairData) => {
  const response = await api.put(`/api/property-repairs/${propertyOwnerId}/${repairId}`, repairData);
  return response;
};

export const deleteRepair = async (propertyOwnerId, repairId) => {
  const response = await api.delete(`/api/property-repairs/${propertyOwnerId}/delete/${repairId}`);
  return response.status;
};
