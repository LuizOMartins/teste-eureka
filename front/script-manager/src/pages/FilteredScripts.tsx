import React, { useState, useEffect } from "react";
import api from "../api/api";

const FilteredScripts: React.FC = () => {
    const [scripts, setScripts] = useState<any[]>([]);
    const [filters, setFilters] = useState({
        status: "",
        dateSent: "",
        email: "",
    });

    const handleInputChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        const { name, value } = event.target;
        setFilters({ ...filters, [name]: value });
    };

    const handleSearch = async () => {
        try {
            const response = await api.get("/scripts/filter", { params: filters });
            setScripts(response.data);
        } catch (error) {
            console.error("Erro ao buscar scripts filtrados:", error);
            alert("Erro ao buscar scripts. Verifique os filtros.");
        }
    };

    useEffect(() => {
        const fetchScripts = async () => {
            try {
                const response = await api.get("/scripts/filter");
                setScripts(response.data);
            } catch (error) {
                console.error("Erro ao carregar scripts:", error);
            }
        };
        fetchScripts();
    }, []);

    return (
        <div>
            <h1>Scripts Filtrados</h1>
            <div style={{ marginBottom: "1rem" }}>
                <label>
                    <strong>Status:</strong>
                    <input
                        type="text"
                        name="status"
                        value={filters.status}
                        onChange={handleInputChange}
                        placeholder="Digite o status"
                        style={{ marginLeft: "0.5rem", marginBottom: "0.5rem" }}
                    />
                </label>
                <br />
                <label>
                    <strong>Data de Envio:</strong>
                    <input
                        type="date"
                        name="dateSent"
                        value={filters.dateSent}
                        onChange={handleInputChange}
                        placeholder="Selecione a data"
                        style={{ marginLeft: "0.5rem", marginBottom: "0.5rem" }}
                    />
                </label>
                <br />
                <label>
                    <strong>Email:</strong>
                    <input
                        type="email"
                        name="email"
                        value={filters.email}
                        onChange={handleInputChange}
                        placeholder="Digite o email"
                        style={{ marginLeft: "0.5rem", marginBottom: "0.5rem" }}
                    />
                </label>
                <br />
                <button onClick={handleSearch} style={{ marginTop: "1rem" }}>
                    Filtrar
                </button>
            </div>
            <table style={{ width: "100%", borderCollapse: "collapse", marginTop: "2rem" }}>
                <thead>
                    <tr>
                        <th style={{ border: "1px solid #ccc", padding: "0.5rem" }}>Script ID</th>
                        <th style={{ border: "1px solid #ccc", padding: "0.5rem" }}>Etapa Atual</th>
                        <th style={{ border: "1px solid #ccc", padding: "0.5rem" }}>Conteúdo</th>
                        <th style={{ border: "1px solid #ccc", padding: "0.5rem" }}>Workflow ID</th>
                        <th style={{ border: "1px solid #ccc", padding: "0.5rem" }}>Etapas do Workflow</th>
                    </tr>
                </thead>
                <tbody>
                    {scripts.map((script, index) => (
                        <tr key={index}>
                            <td style={{ border: "1px solid #ccc", padding: "0.5rem" }}>{script.scriptId}</td>
                            <td
                                style={{
                                    border: "1px solid #ccc",
                                    padding: "0.5rem",
                                    color: script.currentStep ? "green" : "red",
                                }}
                            >
                                {script.currentStep || "Não disponível"}
                            </td>
                            <td style={{ border: "1px solid #ccc", padding: "0.5rem" }}>{script.content}</td>
                            <td style={{ border: "1px solid #ccc", padding: "0.5rem" }}>{script.workflowId}</td>
                            <td style={{ border: "1px solid #ccc", padding: "0.5rem" }}>
                                {script.workflowSteps ? script.workflowSteps.join(", ") : "Não disponível"}
                            </td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );
};

export default FilteredScripts;
