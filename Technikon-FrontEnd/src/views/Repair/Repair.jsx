import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import Button from '@mui/material/Button';
import axios from 'axios';
import List from '@mui/material/List';
import ListItem from '@mui/material/ListItem';
import Box from '@mui/material/Box';
import { useAuth } from '../../hooks/useAuth';


const Repair = () => {
    const { authData } = useAuth();
    const navigate = useNavigate();
    const [repairId, setRepairId] = useState(null);
    const [propertyRepairDto, setPropertyRepairDto] = useState(null);

    if (!authData) {
        return <div>Loading...</div>; // or handle the case when authData is null
    }

    const propertyOwnerId = authData ? authData.id : null;

    const createPropertyRepair = () => {
        navigate('/create-repair');
    };

    const getPropertyRepair = () => {
        api.get(`api/property-repairs/${authData.userId}/${repairId}`)
            .then(response => console.log(response.data))
            .catch(error => console.error(error));
    };

    const getAllRepairs = () => {
        api.get(`http://localhost:5001/api/property-repairs/all`)
            .then(response => console.log(response.data))
            .catch(error => console.error(error));
    };

    const updatePropertyRepair = () => {
        const propertyRepairUpdateDto = {}; // Initialize the update DTO correctly
        axios.put(`api/property-repairs/${propertyOwnerId}/${repairId}`, propertyRepairUpdateDto)
            .then(response => console.log(response.data))
            .catch(error => console.error(error));
    };

    const deletePropertyRepair = () => {
        axios.delete(`api/property-repairs/${propertyOwnerId}/${repairId}`)
            .then(response => console.log(response.data))
            .catch(error => console.error(error));
    };

    return (
        <Box display="flex" flexDirection="column" alignItems="center">
            <List>
                <ListItem>
                    <Button variant="contained" color="primary" onClick={createPropertyRepair}>
                        Create Property Repair
                    </Button>
                </ListItem>
                <ListItem>
                    <Button variant="contained" color="primary" onClick={getPropertyRepair}>
                        Get Property Repair
                    </Button>
                </ListItem>
                <ListItem>
                    <Button variant="contained" color="primary" onClick={getAllRepairs}>
                        Get All Repairs
                    </Button>
                </ListItem>
                <ListItem>
                    <Button variant="contained" color="primary" onClick={updatePropertyRepair}>
                        Update a Repair
                    </Button>
                </ListItem>
                <ListItem>
                    <Button variant="contained" color="primary" onClick={deletePropertyRepair}>
                        Delete a Repair
                    </Button>
                </ListItem>
            </List>
        </Box>
    );
};

export default Repair;
