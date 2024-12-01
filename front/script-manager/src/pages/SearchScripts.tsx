import React, { useState } from "react";
import api from "../api/api";

const SearchScripts: React.FC = () => {
    const [emailOrPhone, setEmailOrPhone] = useState("");
    const [results, setResults] = useState<any[]>([]);

    const handleSearch = async () => {
        try {
            const response = await api.get(`/scripts/by-client`, {
                params: { emailOrPhone },
            });
            setResults(response.data);
        } catch (error) {
            console.error("Erro ao buscar scripts:", error);
            alert("Não foi possível buscar os scripts. Verifique os dados.");
        }
    };

    return (
        <div>
            <h1>Consultar Scripts por Cliente</h1>
            <input
                type="text"
                value={emailOrPhone}
                onChange={(e) => setEmailOrPhone(e.target.value)}
                placeholder="Digite o email ou telefone"
            />
            <button onClick={handleSearch}>Buscar</button>

            {results.length > 0 && (
                <div>
                    <h2>Resultados</h2>
                    {results.map((script, index) => (
                        <div key={index}>
                            <p><strong>Script ID:</strong> {script.scriptId}</p>
                            <p><strong>Current Step:</strong> {script.currentStep}</p>
                            <p><strong>Workflow Steps:</strong> {script.workflowSteps.join(", ")}</p>
                        </div>
                    ))}
                </div>
            )}
        </div>
    );
};

export default SearchScripts;
