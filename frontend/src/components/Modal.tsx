import React from 'react';
import './modal.css'

interface ModalProps {
    isOpen: boolean;
    onClose: () => void;
    inputData: any;
}

const Modal: React.FC<ModalProps> = ({ isOpen, onClose, inputData }) => {
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
                        <span className="list-data__items--title">Nombre del país: </span>
                        <span className="list-data__items--value">{inputData?.countryDTO?.countryName}</span>
                    </p>

                    <p className="list-data__items">
                        <span className="list-data__items--title">Codigo ISO País: </span>
                        <span className="list-data__items--value">{inputData?.countryDTO?.countryIsoCode}</span>
                    </p>

                    <p className="list-data__items">
                        <span className="list-data__items--title">Idiomas: </span>
                        {inputData.countryDTO.countryLanguages.map((lang: {
                            code: string,
                            name: string
                        }, index: number) => (
                            <span key={index} className="list-data__items--value-time">{lang.name} ({lang.code})</span>
                        ))}
                    </p>

                    <p className="list-data__items">
                        <span className="list-data__items--title">Moneda: </span>
                        <span className="list-data__items--value">{inputData?.currencyDTO?.currencyCode} (1 {inputData?.currencyDTO?.currencyCode} = {inputData?.currencyDTO?.exchangeRates?.USD} USD )</span>
                    </p>

                    <p className="list-data__items">
                        <span className="list-data__items--title">Horarios: </span>
                        {inputData.countryDTO.countryTimeZones.map((time: string, index: number) => (
                            <span key={index} className="list-data__items--value-time">{time}</span>
                        ))}
                    </p>

                    <p className="list-data__items">
                        <span
                            className="list-data__items--title">Distancia estimada entre Buenos Aires y el pais: </span>
                        <span className="list-data__items--value-br">{(inputData?.distanceInKmToBA).toFixed(2)} KM </span>
                        <span className="list-data__items--value">
                            ({(inputData?.regionFrom?.latitude)?.toFixed(2)})
                            ({(inputData?.regionFrom?.longitude)?.toFixed(2)})
                            a
                            ({(inputData?.regionTo?.latitude)?.toFixed(2)})
                            ({(inputData?.regionFrom?.longitude)?.toFixed(2)})
                        </span>
                    </p>

                </div>
                <button onClick={onClose}>Cerrar</button>
            </div>
        </div>
    );
};

export default Modal;
