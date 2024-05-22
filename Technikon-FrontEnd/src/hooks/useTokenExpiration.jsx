const useTokenExpiration = (expDate) => {
    return expDate < Date.now() / 1000;
  };
  
  export default useTokenExpiration;
  