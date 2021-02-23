export function Undefined(optionA: any, optionB: any): any {
  return isValid(optionA) ? optionA : optionB;
}

export function isValid(value: any): boolean {
  return value !== undefined && value !== null;
}

