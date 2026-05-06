import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { CustomerService } from '../services/customer-service';
import { Customer } from '../Model/customer.model';
import { NgIf } from '@angular/common';
import { Router } from '@angular/router';

@Component({
  selector: 'app-new-customer',
  imports: [ReactiveFormsModule, NgIf],
  templateUrl: './new-customer.html',
  styleUrl: './new-customer.css',
})
export class NewCustomer implements OnInit {
  newCustomerFormGroup!: FormGroup;

  constructor(
    private formBuilder: FormBuilder,
    private customerService: CustomerService,
    private router: Router,
  ) {}
  ngOnInit(): void {
    this.newCustomerFormGroup = this.formBuilder.group({
      Name: this.formBuilder.control(null, [Validators.required, Validators.minLength(4)]),
      email: this.formBuilder.control(null, [Validators.email, Validators.required]),
    });
  }

  protected hundleSaveCustomer() {
    let customer: Customer = this.newCustomerFormGroup.value;
    console.log(customer);
    this.customerService.saveCustomer(customer).subscribe({
      next: (data) => {
        alert('Customer has been successfully saved');
       // this.newCustomerFormGroup.reset();
        this.router.navigateByUrl('/customers');
      },
      error: (error) => {
        console.log(error);
      },
    });
  }
}
