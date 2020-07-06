export class EnoviaEntityFlatNode {
  constructor(public id: number,
              public type: string,
              public name: string,
              public level: number,
              public expandable = false,
              public isLoading = false) {}
}
