import React, { useState } from "react";
import api from "../api/api";

const SearchScripts: React.FC = () => {
    const [email, setEmail] = useState("");
    const [phone, setPhone] = useState("");
    const [results, setResults] = useState<any[]>([]);

    const handleSearch = async () => {
        try {
            const params: any = {};
            if (email) params.email = email;
            if (phone) params.phone = phone;

            if (!email && !phone) {
                alert("Por favor, insira pelo menos um email ou telefone para a busca.");
                return;
            }

            const response = await api.get(`/scripts/by-client`, { params });
            setResults(response.data);
        } catch (error) {
            console.error("Erro ao buscar scripts:", error);
            alert("Não foi possível buscar os scripts. Verifique os dados.");
        }
    };

    return (
        <div>
            <h1>Consultar Scripts por Cliente</h1>
            <div style={{ marginBottom: "1rem" }}>
                <label>
                    <strong>Email:</strong>
                    <input
                        type="email"
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                        placeholder="Digite o email do cliente"
                        style={{ marginLeft: "0.5rem", marginBottom: "0.5rem" }}
                    />
                </label>
                <br />
                <label>
                    <strong>Telefone:</strong>
                    <input
                        type="text"
                        value={phone}
                        onChange={(e) => setPhone(e.target.value)}
                        placeholder="Digite o telefone do cliente"
                        style={{ marginLeft: "0.5rem" }}
                    />
                </label>
            </div>
            <button onClick={handleSearch}>Buscar</button>

            {results.length > 0 && (
                <div style={{ marginTop: "2rem" }}>
                    <h2>Resultados</h2>
                    {results.map((script, index) => (
                        <div
                            key={index}
                            style={{ border: "1px solid #ccc", padding: "1rem", marginBottom: "1rem" }}
                        >
                            <p>
                                <strong>Script ID:</strong> {script.scriptId}
                            </p>
                            <p>
                                <strong>Etapa Atual:</strong> {script.currentStep || "Não disponível"}
                            </p>
                            <p>
                                <strong>Conteúdo:</strong> {script.content || "Não disponível"}
                            </p>
                            <p>
                                <strong>ID do Workflow:</strong> {script.workflowId || "Não disponível"}
                            </p>
                            <p>
                                <strong>Etapas do Workflow:</strong>
                            </p>
                            <ul>
                                {script.workflowSteps &&
                                    script.workflowSteps.length > 0 &&
                                    script.workflowSteps.map((step: string, stepIndex: number) => (
                                        <li
                                            key={stepIndex}
                                            style={{
                                                color: step === script.currentStep ? "green" : "black",
                                                fontWeight: step === script.currentStep ? "bold" : "normal",
                                            }}
                                        >
                                            {step}
                                        </li>
                                    ))}
                            </ul>
                        </div>
                    ))}
                </div>
            )}

            {results.length === 0 && (email || phone) && (
                <p style={{ marginTop: "1rem", color: "gray" }}>
                    Nenhum script encontrado para os dados fornecidos.
                </p>
            )}
        </div>
    );
};

export default SearchScripts;
