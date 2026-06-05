import { useState, useEffect } from 'react'
import './App.css'

function App() {
    const [status, setStatus] = useState(null)

    useEffect(() => {
        fetch('http://localhost:8080/api/health')
            .then(response => response.json())
            .then(data => setStatus(data))
            .catch(error => console.error('Error fetching status:', error))
    }, [])

    return (
        <div>
            <h1>pindong</h1>
            {status ? (
                <p>서버 상태: {status.status}</p>
            ) : (
                <p>서버 연결 중...</p>
            )}
        </div>
    )
}

export default App
