import React, { useState } from "react";
import SelectWorkflow from "../components/SelectWorkflow";
import axios from "axios";

interface FormData {
    workflowId: string | null;
    clientName: string;
    clientEmail: string;
    clientPhone: string;
    scriptContent: string;
}

const CreateScript: React.FC = () => {
    const [formData, setFormData] = useState<FormData>({
        workflowId: null,
        clientName: "",
        clientEmail: "",
        clientPhone: "",
        scriptContent: ""
    });

    const handleWorkflowChange = (workflowId: string | null) => {
        setFormData({ ...formData, workflowId });
    };

    const handleInputChange = (event: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
        const { name, value } = event.target;
        setFormData({ ...formData, [name]: value });
    };

    const handleSubmit = (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault();

        axios.post("http://localhost:9090/api/scripts", {
            workflowId: formData.workflowId,
            client: {
                name: formData.clientName,
                email: formData.clientEmail,
                phone: formData.clientPhone
            },
            content: formData.scriptContent
        })
            .then(response => {
                alert("Script criado com sucesso!");
                setFormData({
                    workflowId: null,
                    clientName: "",
                    clientEmail: "",
                    clientPhone: "",
                    scriptContent: ""
                });
            })
            .catch(error => {
                console.error("Erro ao criar script:", error);
                alert("Erro ao criar script!");
            });
    };

    return (
        <form onSubmit={handleSubmit}>
            <div>
                <label>Workflow:</label>
                <SelectWorkflow onWorkflowChange={handleWorkflowChange} />
            </div>
            <div>
                <label>Nome do Cliente:</label>
                <input
                    type="text"
                    name="clientName"
                    value={formData.clientName}
                    onChange={handleInputChange}
                    required
                />
            </div>
            <div>
                <label>Email do Cliente:</label>
                <input
                    type="email"
                    name="clientEmail"
                    value={formData.clientEmail}
                    onChange={handleInputChange}
                    required
                />
            </div>
            <div>
                <label>Telefone do Cliente:</label>
                <input
                    type="text"
                    name="clientPhone"
                    value={formData.clientPhone}
                    onChange={handleInputChange}
                    required
                />
            </div>
            <div>
                <label>Conteúdo do Script:</label>
                <textarea
                    name="scriptContent"
                    value={formData.scriptContent}
                    onChange={handleInputChange}
                    required
                ></textarea>
            </div>
            <button type="submit">Criar Script</button>
        </form>
    );
};

export default CreateScript;
