import { useState } from 'react';
import { useForm } from 'react-hook-form';
import Modal from "./Modal.tsx";
import './formulario.css';
import ModalReport from "./ModalReport.tsx";

const Formulario = () => {
    const { register, formState: { errors }, handleSubmit } = useForm();
    const [ipData, setIpData] = useState<any>(null);
    const [error, setError] = useState<string | null>(null);
    const [isModalOpen, setIsModalOpen] = useState(false);
    const [isReportModalOpen, setIsReportModalOpen] = useState(false);
    const [reportData, setReportData] = useState<any>(null);

    const onSubmit = async (data: any) => {
        try {
            const response = await fetch(`http://localhost:8080/api-geo/geolocation/findDataIp?ip=${data?.ip}`);

            if (!response.ok) throw new Error("Error en la respuesta del servidor");

            const result = await response.json();
            setIpData(result);
            setError(null);
            setIsModalOpen(true); // Abre el modal
        } catch (err) {
            setError("Error al obtener los datos");
            setIpData(null);
            console.error(err);
        }
    };

    const handleGenerateReport = async () => {
        try {
            const response = await fetch('http://localhost:8080/api-geo/statistic/generateReport');

            if (!response.ok) throw new Error("Error al generar el reporte");

            const result = await response.json();
            setReportData(result);
            setError(null);
            setIsReportModalOpen(true);
        } catch (err) {
            setError("Error al generar el reporte");
            setReportData(null);
            console.error(err);
        }
    };

    return (
        <div className="formulario">
            <h2>Formulario</h2>

            <form className="form" onSubmit={handleSubmit(onSubmit)}>
                <div className="formItems">
                    <label>IP</label>
                    <input
                        type="text"
                        {...register('ip', {
                            required: true,
                            maxLength: 16
                        })}
                    />
                </div>

                <input className="button" type="submit" value="Enviar"/>
            </form>

            {/* Modal que recibe el valor del input */}
            <Modal
                isOpen={isModalOpen}
                onClose={() => setIsModalOpen(false)}
                inputData={ipData}
            />

            <div>
                {error && <p style={{color: 'red'}}>{error}</p>}
                {errors.ip?.type === 'required' && <p style={{color: 'red'}}>La IP es obligatoria</p>}
            </div>

            <input className="button button2" type="button" value="Generar Reporte" onClick={handleGenerateReport}/>

            <ModalReport
                isOpen={isReportModalOpen}
                onClose={() => setIsReportModalOpen(false)}
                inputData={reportData}
            />

        </div>
    );
};

export default Formulario;
