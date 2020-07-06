import {Component, Input, Output, EventEmitter, forwardRef} from '@angular/core';
import { COLORS } from './colors';
import {ControlValueAccessor, NG_VALUE_ACCESSOR} from '@angular/forms';

@Component({
  selector: 'app-color-picker',
  templateUrl: './color-picker.component.html',
  styleUrls: ['./color-picker.component.scss'],
  providers: [{
    provide: NG_VALUE_ACCESSOR,
    useExisting: forwardRef(() => ColorPickerComponent),
    multi: true
  }]
})
export class ColorPickerComponent implements ControlValueAccessor {
  @Input() heading: string;
  @Input() color: string;
  @Output('onColorPick') event: EventEmitter<string> = new EventEmitter<string>();
  public show = false;
  public defaultColors: string[] = COLORS;
  onChange: (value: string) => void = () => {};
  onTouched: () => void = () => {};
  constructor() { }
    writeValue(obj: any): void {
        this.color = obj;
    }
    registerOnChange(fn: any): void {
        this.onChange = fn;
    }
    registerOnTouched(fn: any): void {
        this.onTouched = fn;
    }
    setDisabledState?(isDisabled: boolean): void {
    }
  /**
   * Change color from default colors
   */
  public changeColor(color: string): void {
    this.color = color;
    this.event.emit(this.color);
    this.show = false;
    this.onChange(color);
    this.onTouched();
  }

  /**
   * Change color from input
   */
  public changeColorManual(color: string): void {
    const isValid = /(^#[0-9A-F]{6}$)|(^#[0-9A-F]{3}$)/i.test(color);

    if (isValid) {
      this.color = color;
      this.event.emit(this.color);
    }
    this.onChange(color);
    this.onTouched();
  }

  /**
   * Change status of visibility to color picker
   */
  public toggleColors(): void {
    this.show = !this.show;
  }
}
