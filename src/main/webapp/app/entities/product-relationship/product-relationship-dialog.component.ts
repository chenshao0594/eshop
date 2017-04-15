import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { ProductRelationship } from './product-relationship.model';
import { ProductRelationshipPopupService } from './product-relationship-popup.service';
import { ProductRelationshipService } from './product-relationship.service';
import { Product, ProductService } from '../product';

@Component({
    selector: 'jhi-product-relationship-dialog',
    templateUrl: './product-relationship-dialog.component.html'
})
export class ProductRelationshipDialogComponent implements OnInit {

    productRelationship: ProductRelationship;
    authorities: any[];
    isSaving: boolean;

    products: Product[];
    constructor(
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private productRelationshipService: ProductRelationshipService,
        private productService: ProductService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['productRelationship']);
        this.productRelationship = new ProductRelationship();
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
        if (this.productRelationship.id !== undefined) {
            this.productRelationshipService.update(this.productRelationship)
                .subscribe((res: ProductRelationship) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.productRelationshipService.create(this.productRelationship)
                .subscribe((res: ProductRelationship) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: ProductRelationship) {
        this.eventManager.broadcast({ name: 'productRelationshipListModification', content: 'OK'});
        this.isSaving = false;
        this.productRelationship = result;
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
    selector: 'jhi-product-relationship-popup',
    template: ''
})
export class ProductRelationshipPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private productRelationshipPopupService: ProductRelationshipPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.productRelationshipPopupService
                    .open(ProductRelationshipDialogComponent, params['id']);
            } else {
                this.modalRef = this.productRelationshipPopupService
                    .open(ProductRelationshipDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
