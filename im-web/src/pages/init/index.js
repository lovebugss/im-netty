import React, {useEffect, useState} from 'react';
import {Button, Form, Input, message} from 'antd';
import {connectInit} from '@/services/channel';
import {history} from "umi";


const Init = () => {
  const [messageApi, contextHolder] = message.useMessage();

  const onFinish = async (values) => {
    console.log('Success:', values);
    const resp = await connectInit({...values})
    if (resp.code === 200) {
      history.push({
        pathname: '/chat',
        query: {
          ...resp.data,
          uid: values.userId
        }
      })
    } else {
      messageApi.open({
        type: 'error',
        content: resp.message,
      });
    }
  };
  const onFinishFailed = (errorInfo) => {
    console.log('Failed:', errorInfo);
  };
  return (
    <Form
      name="basic"
      labelCol={{
        span: 8,
      }}
      wrapperCol={{
        span: 16,
      }}
      initialValues={{
        remember: true,
      }}
      onFinish={onFinish}
      onFinishFailed={onFinishFailed}
      autoComplete="off"
    >
      <Form.Item
        label="Channel ID"
        name="channelId"
        rules={[
          {
            required: true,
            message: 'Please input Channel ID!',
          },
        ]}
      >
        <Input
          defaultValue={"ch_00000001"}
          value={"ch_00000001"}
        />
      </Form.Item>

      <Form.Item
        label="Username"
        name="userId"
        rules={[
          {
            required: true,
            message: 'Please input your Username!',
          },
        ]}
      >
        <Input/>
      </Form.Item>

      <Form.Item
        wrapperCol={{
          offset: 8,
          span: 16,
        }}
      >
        <Button type="primary" htmlType="submit">
          进入聊天室
        </Button>
      </Form.Item>
    </Form>
  );
};
export default Init
