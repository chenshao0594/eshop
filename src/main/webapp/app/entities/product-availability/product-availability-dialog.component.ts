import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { ProductAvailability } from './product-availability.model';
import { ProductAvailabilityPopupService } from './product-availability-popup.service';
import { ProductAvailabilityService } from './product-availability.service';
import { Product, ProductService } from '../product';

@Component({
    selector: 'jhi-product-availability-dialog',
    templateUrl: './product-availability-dialog.component.html'
})
export class ProductAvailabilityDialogComponent implements OnInit {

    productAvailability: ProductAvailability;
    authorities: any[];
    isSaving: boolean;

    products: Product[];
        constructor(
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private productAvailabilityService: ProductAvailabilityService,
        private productService: ProductService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['productAvailability']);
        this.productAvailability = new ProductAvailability();
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.productService.query().subscribe(
            (res: Response) => { this.products = res.json(); }, (res: Response) => this.onError(res.json()));
    }
    clear() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.productAvailability.id !== undefined) {
            this.productAvailabilityService.update(this.productAvailability)
                .subscribe((res: ProductAvailability) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.productAvailabilityService.create(this.productAvailability)
                .subscribe((res: ProductAvailability) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: ProductAvailability) {
        this.eventManager.broadcast({ name: 'productAvailabilityListModification', content: 'OK'});
        this.isSaving = false;
        this.productAvailability = result;
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

    trackProductById(index: number, item: Product) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-product-availability-popup',
    template: ''
})
export class ProductAvailabilityPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private productAvailabilityPopupService: ProductAvailabilityPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.productAvailabilityPopupService
                    .open(ProductAvailabilityDialogComponent, params['id']);
            } else {
                this.modalRef = this.productAvailabilityPopupService
                    .open(ProductAvailabilityDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
