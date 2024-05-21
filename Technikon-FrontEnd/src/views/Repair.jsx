import React, { useState } from 'react';
import Button from '@mui/material/Button';
import axios from 'axios';
import List from '@mui/material/List';
import ListItem from '@mui/material/ListItem';
import Box from '@mui/material/Box';
import api from '../api/Api';
import { useAuth } from '../hooks/useAuth';

const Repair = () => {
    const { authData } = useAuth(); // Move this line up to declare authData first
    const propertyOwnerId = authData ? authData.id : null;
    const [repairId, setRepairId] = useState(null);
    const [propertyRepairDto, setPropertyRepairDto] = useState(null);

    // Now you can use authData in your component.
    // For example, you might want to render different content based on whether authData is null:

    const createPropertyRepair = () => {
        axios.post(`api/property-repairs`, propertyRepairDto)
            .then(response => {
                console.log(response.data);
                setRepairId(response.data.id);
            })
            .catch(error => console.error(error));
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
