import styles from './index.less';
import Init from './init'

export default function IndexPage() {
  return (
    <div>
      <h1 className={styles.title}>Chat Demo</h1>
      <Init/>
    </div>
  );
}
