import api from '../../api/Api';

export const createProperty = async (propertyData) => {
    const response = await api.post('/api/property/create', propertyData);
    return response.data;
}

export const fetchProperty = async (propertyOwnerId) => {
  const response = await api.get(`/api/property/properties/${propertyOwnerId}`);
  console.log(response.data);
  return response.data;
};

export const searchPropertiesByTin = async (propertyOwnerId, tin) => {
  const response = await api.get(`/api/property/tin/${propertyOwnerId}/get-by-tin/${tin}`);
  return response.data;
};

export const searchPropertiesByArea = async (propertyOwnerId,longitude ,latitude) => {
  const response = await api.get(`/api/property/propertyArea/${propertyOwnerId}/get-by-longitute-latitude/${longitude}/${latitude}`);
  return response.data;
};

export const updateProperties = async ( propertyId, data) => {
  const response = await api.put(`/api/property/update/${propertyId}`, data);
  return response.data;
};

export const deleteProperties = async (propertyId) => {
  const response = await api.delete(`/api/property/delete/${propertyId}`);
  return response.status;
}

export const getAllProperties = async() =>  {
  const response = await api.get('/api/property/getAllProperties/')
  return response;
};