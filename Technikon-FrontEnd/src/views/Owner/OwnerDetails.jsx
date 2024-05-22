

const OwnerDetails = ({ ownerDetails }) => (
  <div>
    <h1>Owner Details</h1>
    <p>User TIN: {ownerDetails.tin}</p>
    <p>Username: {ownerDetails.username}</p>
    <p>User Email: {ownerDetails.email}</p>
    <p>User First Name: {ownerDetails.name}</p>
    <p>User Last Name: {ownerDetails.surname}</p>
    <p>User Address: {ownerDetails.address}</p>
    <p>User Phone number: {ownerDetails.phoneNumber}</p>
  </div>
);

export default OwnerDetails;
