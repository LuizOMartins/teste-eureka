import React, { useState, useEffect } from 'react';
import axios from 'axios';

const SelectWorkflow = ({ onWorkflowChange }) => {
    const [workflows, setWorkflows] = useState([]);
    const [selectedWorkflow, setSelectedWorkflow] = useState("");

    useEffect(() => {
        axios.get("http://localhost:9090/api/workflows")
            .then(response => {
                setWorkflows(response.data);
            })
            .catch(error => {
                console.error("Erro ao buscar workflows:", error);
            });
    }, []);

    const handleChange = (event) => {
        const selectedId = event.target.value;
        setSelectedWorkflow(selectedId);
        if (onWorkflowChange) {
            onWorkflowChange(selectedId); // Notificar o componente pai
        }
    };

    return (
        <select value={selectedWorkflow} onChange={handleChange}>
            <option value="">Selecione um Workflow</option>
            {workflows.map(workflow => (
                <option key={workflow.id} value={workflow.id}>
                    {workflow.name}
                </option>
            ))}
        </select>
    );
};

export default SelectWorkflow;
