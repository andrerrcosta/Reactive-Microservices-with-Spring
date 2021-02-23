export function Percent(total: number, value: number, float?: number) {
  return float
    ? Math.round(((value * 100) / total) * Math.pow(10, float)) /
        Math.pow(10, float)
    : (value * 100) / total;
}
