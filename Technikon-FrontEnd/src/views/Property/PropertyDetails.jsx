import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { fetchProperty } from '../../api/Api'; 

const PropertyDetails = () => {
    const { propertyId } = useParams();
    const [property, setProperty] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        const getPropertyDetails = async () => {
            try {
                const data = await fetchProperty(propertyId);
                setProperty(data);
                setLoading(false);
            } catch (err) {
                setError(err);
                setLoading(false);
            }
        };

        getPropertyDetails();
    }, [propertyId]);

    if (loading) return <div>Loading...</div>;
    if (error) return <div>Error: {error.message}</div>;

    return (
        <div>
            <h1>Property Details</h1>
            <p>ID: {property.id}</p>
            <p>Name: {property.name}</p>
            <p>Location: {property.location}</p>
            {/* Add more details as necessary */}
        </div>
    );
};

export default PropertyDetails;
