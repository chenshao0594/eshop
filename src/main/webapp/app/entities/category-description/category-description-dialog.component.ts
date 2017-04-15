import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { CategoryDescription } from './category-description.model';
import { CategoryDescriptionPopupService } from './category-description-popup.service';
import { CategoryDescriptionService } from './category-description.service';
import { Category, CategoryService } from '../category';

@Component({
    selector: 'jhi-category-description-dialog',
    templateUrl: './category-description-dialog.component.html'
})
export class CategoryDescriptionDialogComponent implements OnInit {

    categoryDescription: CategoryDescription;
    authorities: any[];
    isSaving: boolean;

    categories: Category[];
    constructor(
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private categoryDescriptionService: CategoryDescriptionService,
        private categoryService: CategoryService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['categoryDescription']);
        this.categoryDescription = new CategoryDescription();
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.categoryService.query().subscribe(
            (res: Response) => { this.categories = res.json(); }, (res: Response) => this.onError(res.json()));
    }
    clear() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.categoryDescription.id !== undefined) {
            this.categoryDescriptionService.update(this.categoryDescription)
                .subscribe((res: CategoryDescription) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.categoryDescriptionService.create(this.categoryDescription)
                .subscribe((res: CategoryDescription) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: CategoryDescription) {
        this.eventManager.broadcast({ name: 'categoryDescriptionListModification', content: 'OK'});
        this.isSaving = false;
        this.categoryDescription = result;
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

    trackCategoryById(index: number, item: Category) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-category-description-popup',
    template: ''
})
export class CategoryDescriptionPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private categoryDescriptionPopupService: CategoryDescriptionPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.categoryDescriptionPopupService
                    .open(CategoryDescriptionDialogComponent, params['id']);
            } else {
                this.modalRef = this.categoryDescriptionPopupService
                    .open(CategoryDescriptionDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
