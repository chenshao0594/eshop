import { Component, OnInit, OnDestroy, ViewEncapsulation } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { Response } from '@angular/http';
import { EventManager, AlertService, JhiLanguageService, DataUtils } from 'ng-jhipster';

import { Product } from './product.model';
import { ProductService } from './product.service';
import { AttachmentService } from '../attachment/attachment.service';
import { Attachment } from '../attachment/attachment.model';

const URL = '/api/attachments';

@Component( {
    templateUrl: './product-attachment.component.html',
    styleUrls: [ './fileupload.style.scss' ]
} )
export class ProductAttachmentComponent  implements OnInit {
    product: Product;
    boId: number;
    attachment: Attachment;
    attachments : Attachment[];
    private subscription: any;
    private eventSubscriber: Subscription;
    isSaving: boolean;

    constructor(
        private eventManager: EventManager,
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private productService: ProductService,
        private attachmentService: AttachmentService,
        private dataUtils: DataUtils,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['product']);
        this.jhiLanguageService.setLocations(['attachment']);
        this.isSaving = false;
    
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.boId = params['id'];
            this.loadAttachments(params['id']);
            });
         this.registerChangeInProductAttachments();
    }
    loadAttachments(productId){
        this.attachmentService.queryAttachmentsByBO('products',this.boId).subscribe(
                (res: Response) => { this.attachments = res.json(); }, 
                (res: Response) => this.onError(res.json()));
    }
    
    registerChangeInProductAttachments() {
        this.eventSubscriber = this.eventManager.subscribe('attachmentListModification', (response) => this.loadAttachments(this.boId));
    }
    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData(event, attachment, field, isImage) {
        if (event.target.files && event.target.files[0]) {
            const file = event.target.files[0];
            attachment = new Attachment();
            attachment['boName'] ='product';
            attachment['boId'] = this.boId;
            attachment['name'] = file.name;
            attachment['size'] = file.size;
            if (isImage && !/^image\//.test(file.type)) {
                return;
            }
            this.dataUtils.toBase64(file, (base64Data) => {
                attachment[field] = base64Data;
                attachment[`${field}ContentType`] = file.type;
                this.upload(attachment);
            });
        }
    }
    upload(attachment: Attachment){
         this.attachmentService.create(attachment)
        .subscribe((res: Attachment) =>
        this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));

    }
    clear() {
        // this.activeModal.dismiss('cancel');
    }
    
    back() {
        window.history.back();
    }
    
    deleteAttachment(attachmentId: number) {
        this.attachmentService.delete(attachmentId)
                .subscribe((res: Attachment) =>this.onDeleteSuccess(res), 
                        (res: Response) => this.onSaveError(res));
    }

    private onDeleteSuccess(result: any) {
        this.eventManager.broadcast({ name: 'attachmentListModification', content: 'OK'});
    }
    save() {
        this.attachmentService.create(this.attachment)
            .subscribe((res: Attachment) =>
                this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Attachment) {
        this.eventManager.broadcast({ name: 'attachmentListModification', content: 'OK'});
        this.isSaving = false;
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
}
