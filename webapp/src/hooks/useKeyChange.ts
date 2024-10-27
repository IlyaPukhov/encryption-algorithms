import { useState } from 'react';

export const useKeyChange = (initialValue: number) => {
  const [key, setKey] = useState<number>(initialValue);

  const handleKeyChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const value = parseInt(e.target.value);
    if (value > 0) {
      setKey(value);
    } else {
      setKey(1);
    }
  };

  return { key, handleKeyChange };
};
