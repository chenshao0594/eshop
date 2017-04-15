import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { ContentDescription } from './content-description.model';
import { ContentDescriptionPopupService } from './content-description-popup.service';
import { ContentDescriptionService } from './content-description.service';
import { Content, ContentService } from '../content';

@Component({
    selector: 'jhi-content-description-dialog',
    templateUrl: './content-description-dialog.component.html'
})
export class ContentDescriptionDialogComponent implements OnInit {

    contentDescription: ContentDescription;
    authorities: any[];
    isSaving: boolean;

    contents: Content[];
    constructor(
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private contentDescriptionService: ContentDescriptionService,
        private contentService: ContentService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['contentDescription']);
        this.contentDescription = new ContentDescription();
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.contentService.query().subscribe(
            (res: Response) => { this.contents = res.json(); }, (res: Response) => this.onError(res.json()));
    }
    clear() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.contentDescription.id !== undefined) {
            this.contentDescriptionService.update(this.contentDescription)
                .subscribe((res: ContentDescription) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.contentDescriptionService.create(this.contentDescription)
                .subscribe((res: ContentDescription) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: ContentDescription) {
        this.eventManager.broadcast({ name: 'contentDescriptionListModification', content: 'OK'});
        this.isSaving = false;
        this.contentDescription = result;
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

    trackContentById(index: number, item: Content) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-content-description-popup',
    template: ''
})
export class ContentDescriptionPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private contentDescriptionPopupService: ContentDescriptionPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.contentDescriptionPopupService
                    .open(ContentDescriptionDialogComponent, params['id']);
            } else {
                this.modalRef = this.contentDescriptionPopupService
                    .open(ContentDescriptionDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
