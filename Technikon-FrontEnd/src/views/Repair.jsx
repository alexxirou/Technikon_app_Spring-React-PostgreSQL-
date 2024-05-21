import React, { useState, useEffect } from 'react';
import { Container, Typography, Button, TextField, Box } from '@mui/material';

const Repair = () => {
    const [repairs, setRepairs] = useState([]);
    const [searchTerm, setSearchTerm] = useState('');

    useEffect(() => {
        fetch('/api/property-repairs/all-by-owner/{propertyOwnerId}') 
            .then(response => response.json())
            .then(data => setRepairs(data));
    }, []);

    const handleCreateRepair = () => {
        const newRepair = {
            dateOfRepair: '2024-05-21',
            shortDescription: 'New Repair',
            repairType: 'Type1',
            repairStatus: 'Status1',
            cost: 100,
            longDescription: 'This is a new repair.'
        };

        fetch('/api/property-repairs', { 
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(newRepair),
        })
        .then(response => response.json())
        .then(data => setRepairs(prevRepairs => [...prevRepairs, data]));
    };

    const handleDeleteRepair = (id) => {
        fetch(`/api/property-repairs/{propertyOwnerId}/${id}`, { 
            method: 'DELETE',
        })
        .then(() => setRepairs(prevRepairs => prevRepairs.filter(repair => repair.id !== id)));
    };

    const handleSearch = (event) => {
        setSearchTerm(event.target.value);
    };

    return (
        <Container maxWidth="sm">
            <Typography variant="h2" align="center" gutterBottom>
                Property Repairs
            </Typography>
            <Box sx={{ mb: 2 }}>
                <Button variant="contained" color="primary" onClick={handleCreateRepair}>
                    Create New Repair
                </Button>
            </Box>
            <TextField label="Search Repairs" variant="outlined" fullWidth value={searchTerm} onChange={handleSearch} />
            {repairs.filter(repair => repair.shortDescription.includes(searchTerm)).map(repair => (
                <Box key={repair.id} sx={{ mb: 2 }}>
                    <Typography variant="h6">{repair.shortDescription}</Typography>
                    <Typography variant="body1">{repair.longDescription}</Typography>
                    <Button variant="contained" color="secondary" onClick={() => handleDeleteRepair(repair.id)}>
                        Delete Repair
                    </Button>
                </Box>
            ))}
        </Container>
    );
};

export default Repair;
