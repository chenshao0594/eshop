import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { ProductPrice } from './product-price.model';
import { ProductPricePopupService } from './product-price-popup.service';
import { ProductPriceService } from './product-price.service';
import { ProductAvailability, ProductAvailabilityService } from '../product-availability';

@Component({
    selector: 'jhi-product-price-dialog',
    templateUrl: './product-price-dialog.component.html'
})
export class ProductPriceDialogComponent implements OnInit {

    productPrice: ProductPrice;
    authorities: any[];
    isSaving: boolean;

    productavailabilities: ProductAvailability[];
                    constructor(
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private productPriceService: ProductPriceService,
        private productAvailabilityService: ProductAvailabilityService,
        private eventManager: EventManager,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['productPrice', 'productPriceType']);
    }

    ngOnInit() {
        this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.load(params['id']);
            } else {
                this.productPrice = new ProductPrice();
            }
        });
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.productAvailabilityService.query().subscribe(
            (res: Response) => { this.productavailabilities = res.json(); }, (res: Response) => this.onError(res.json()));
    }
    load(id) {
         this.productPriceService.find(id).subscribe((productPrice) => {
                this.productPrice = productPrice;
                
         });
    }
    clear() {
       // this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.productPrice.id !== undefined) {
            this.productPriceService.update(this.productPrice)
                .subscribe((res: ProductPrice) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.productPriceService.create(this.productPrice)
                .subscribe((res: ProductPrice) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: ProductPrice) {
        this.eventManager.broadcast({ name: 'productPriceListModification', content: 'OK'});
        this.isSaving = false;
      //  this.activeModal.dismiss(result);
    }

    private onSaveError(error) {
        try {
            error.json();
        } catch (exception) {
            error.message = error.text();
        }
        this.isSaving = false;
        this.onError(error);
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }

    trackProductAvailabilityById(index: number, item: ProductAvailability) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-product-price-popup',
    template: ''
})
export class ProductPricePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private productPricePopupService: ProductPricePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.productPricePopupService
                    .open(ProductPriceDialogComponent, params['id']);
            } else {
                this.modalRef = this.productPricePopupService
                    .open(ProductPriceDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
