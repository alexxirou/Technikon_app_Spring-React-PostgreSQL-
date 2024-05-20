import axios from "axios";
import { useState, useEffect } from "react";

const Admin = () => {
  const [joke, setJoke] = useState(null);
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    axios
      .get("https://api.chucknorris.io/jokes/random")
      .then((data) => {
        console.log(data);
        setJoke(data.data.value);
        setIsLoading(false);
      })
      .catch((error) => {
        console.log(error);
      });
  }, []);

  return (
    <div>
      <h1>Admin page</h1>
      <div>{isLoading ? <h3>Loading...</h3> : <h3>{joke}</h3>}</div>
    </div>
  );
};

export default Admin;
