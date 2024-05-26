export const validateCost = (cost, setErrors) => {
    if (cost <= 0) {
      setErrors((prevErrors) => ({
        ...prevErrors,
        cost: 'Insert a valid cost'
      }));
      return false;
    } else {
      setErrors((prevErrors) => {
        const { cost, ...rest } = prevErrors;
        return rest;
      });
      return true;
    }
  };
  
  export const validateDate = (date, setErrors) => {
    const today = new Date().toISOString().split('T')[0];
    if (date < today) {
      setErrors((prevErrors) => ({
        ...prevErrors,
        dateOfRepair: 'Date of repair cannot be in the past'
      }));
      return false;
    } else {
      setErrors((prevErrors) => {
        const { dateOfRepair, ...rest } = prevErrors;
        return rest;
      });
      return true;
    }
  };
  