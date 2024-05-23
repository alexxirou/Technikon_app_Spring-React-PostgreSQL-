import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { Button, List, ListItem, Box, Typography } from '@mui/material';
import { useAuth } from '../../hooks/useAuth';
import { PATHS } from '../../lib/constants';


const Repair = () => {
    const { authData } = useAuth();
    const navigate = useNavigate();
    const [repairId, setRepairId] = useState(null);

    if (!authData) {
        return <div>Loading...</div>; // or handle the case when authData is null
    }

    const propertyOwnerId = authData ? authData.userId : null;
    console.log(propertyOwnerId);

    const createPropertyRepair = () => {
        navigate('/create-repair');
    };

    const showAllRepairs = () => {

        navigate(PATHS.SHOW_REPAIRS);
    };

    return (
        <Box display="flex" flexDirection="column" alignItems="center">
            <List>
                <ListItem>
                    <Button variant="contained" color="primary" onClick={createPropertyRepair}>
                        Create property repair
                    </Button>
                    <Button variant="contained" color="primary" onClick={showAllRepairs}>
                        Show my repairs
                    </Button>
                </ListItem>
            </List>
        </Box>
    );
};

export default Repair;