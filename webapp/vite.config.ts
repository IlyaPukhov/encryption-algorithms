import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

const REACT_PORT: number = 8090;

export default defineConfig({
  plugins: [react()],
  server: {
    port: REACT_PORT,
    strictPort: true,
  },
})
