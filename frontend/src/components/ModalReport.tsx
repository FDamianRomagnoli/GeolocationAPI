import React from 'react';
import './modalReport.css'

interface ModalProps {
    isOpen: boolean;
    onClose: () => void;
    inputData: any;
}

const ModalReport: React.FC<ModalProps> = ({ isOpen, onClose, inputData }) => {
    if (!isOpen) return null;

    // Aquí aseguramos que los estilos sean del tipo correcto para React
    const styles: {
        modalBackground: {
            backgroundColor: string;
            top: number;
            borderRadius: string;
            alignItems: string;
            left: number;
            bottom: number;
            display: string;
            position: "fixed";
            right: number;
            justifyContent: string
        };
        modalContainer: {
            padding: string;
            backgroundColor: string;
            borderRadius: string;
            textAlign: "center";
            width: string
        }
    } = {
        modalBackground: {
            position: 'fixed' as 'fixed', // Especificamos el tipo como 'fixed'
            top: 0,
            left: 0,
            right: 0,
            bottom: 0,
            backgroundColor: 'rgba(0, 0, 0, 0.1)',
            borderRadius: '15px',
            display: 'flex',
            justifyContent: 'center',
            alignItems: 'center',
        },
        modalContainer: {
            backgroundColor: 'white',
            padding: '20px',
            borderRadius: '8px',
            width: '400px',
            textAlign: 'center' as 'center', // Especificamos el tipo como 'center'
        },
    };

    return (
        <div style={styles.modalBackground}>
            <div style={styles.modalContainer}>
                <h2>Geolocality Data</h2>
                <div className="list-data">
                    <p className="list-data__items">
                        <span className="list-data__items--title">Distancia mas lejana </span>
                        <span className="list-data__items--value">
                            {inputData?.geolocationMaxDistance?.regionDtoFrom?.countryName} ( {(inputData?.geolocationMaxDistance?.distanceInKm)?.toFixed(2)} KM con {inputData?.geolocationMaxDistance?.invocationCount} invocaciones )
                        </span>
                    </p>

                    <p className="list-data__items">
                        <span className="list-data__items--title">Distancia mas cercana </span>
                        <span className="list-data__items--value">
                            {inputData?.geolocationMinDistance?.regionDtoFrom?.countryName} ( {(inputData?.geolocationMinDistance?.distanceInKm)?.toFixed(2)} KM con {inputData?.geolocationMinDistance?.invocationCount} invocaciones )
                        </span>
                    </p>

                    <p className="list-data__items">
                        <span className="list-data__items--title">Distancia promedio de todas las ejecuciones</span>
                        <span className="list-data__items--value">
                            {(inputData?.averageDistance)?.toFixed(2)} KM

                        </span>
                    </p>
                </div>
                <button onClick={onClose}>Cerrar</button>
            </div>
        </div>
    );
};

export default ModalReport;
