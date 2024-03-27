export interface User {
  id: number;
  name: string;
  role: string;
  description: string;
  achievements: string[];
}

export class UserImpl implements User {
  constructor(public id: number, public name: string, public role: string, public description: string, public achievements: string[]) {}
}