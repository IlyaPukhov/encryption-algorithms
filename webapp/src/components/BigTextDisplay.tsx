import React from "react";
import { Tooltip, Typography } from "@mui/material";

export const BigTextDisplay: React.FC<{ value: any; variant: "body1" | "h5" }> = ({value, variant}) => {
  const text = value.toString();
  const isLongText = text.length > 10;

  return (
    <Typography variant={variant} sx={{ display: 'inline-flex' }}>
      <strong>
        {isLongText ? (
          <span>
            {text.slice(0, 5)}
            <Tooltip title={text} arrow>
              <span style={{cursor: "pointer"}}>...</span>
            </Tooltip>
            {text.slice(-5)}
          </span>
        ) : (
          text
        )}
      </strong>
    </Typography>
  );
};
