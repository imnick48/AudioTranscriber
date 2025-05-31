import { useState } from "react";
import axios from 'axios';

const AudioUploaderComp = () => {
    const [file, setFile] = useState<File | null>(null);
    const [transcription, setTranscription] = useState("");

    const handleFileChange = (e:React.ChangeEvent<HTMLInputElement>) => {
        if (e.target.files && e.target.files.length > 0) {
            setFile(e.target.files[0]);
        }
    };

    const handleUpload = async () => {
        const formData = new FormData();
        if(file){
            formData.append('file', file);
        }

        try {
            const response = await axios.post('http://localhost:8080/transcribe/api', formData, {
                headers: {
                    'Content-Type':'multipart/form-data',
                }
            });
            setTranscription(response.data);
        } catch (error) {
            console.error("Error transcribing audio", error);
        }
    };


    return(
        <div className="container">
            <h1> Audio to Text Transcriber</h1>
            <div className="file-input">
                <input type="file" accept="audio/*" onChange={handleFileChange} />
            </div>
            <button className="upload-button" onClick={handleUpload}>Upload and Transcribe</button>
            <div className="transcription-result">
                <h2>Transcription Result</h2>
                <p>{transcription}</p>
            </div>
        </div>
    );
}

export default AudioUploaderComp;