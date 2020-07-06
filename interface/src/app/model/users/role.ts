import {Permission} from './permission';

export interface Role {
  mnemonic: string;
  roleName: string;
  description: string;
  permissions: Permission[];
}
