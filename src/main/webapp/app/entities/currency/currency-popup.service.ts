import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { Currency } from './currency.model';
import { CurrencyService } from './currency.service';
@Injectable()
export class CurrencyPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private currencyService: CurrencyService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.currencyService.find(id).subscribe((currency) => {
                if (currency.created_date) {
                    currency.created_date = {
                        year: currency.created_date.getFullYear(),
                        month: currency.created_date.getMonth() + 1,
                        day: currency.created_date.getDate()
                    };
                }
                if (currency.last_modified_date) {
                    currency.last_modified_date = {
                        year: currency.last_modified_date.getFullYear(),
                        month: currency.last_modified_date.getMonth() + 1,
                        day: currency.last_modified_date.getDate()
                    };
                }
                this.currencyModalRef(component, currency);
            });
        } else {
            return this.currencyModalRef(component, new Currency());
        }
    }

    currencyModalRef(component: Component, currency: Currency): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.currency = currency;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        });
        return modalRef;
    }
}
